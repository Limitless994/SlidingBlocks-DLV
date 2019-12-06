package klotski.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javafx.util.Pair;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;

/**
 * Represents the entire game board, containing several pieces
 * @author Joseph Petitti
 *
 */

public class Board {
	public String encodingResource="encodings/SlidingBlocks-Rules";
	public String instanceResource="encodings/SlidingBlocks-instance";
	public Handler handler = new DesktopHandler(new DLVDesktopService("lib/dlv.mingw.exe"));
	public InputProgram  program = new ASPInputProgram();
	List<String> listaMosse = new ArrayList<String>();
	List<Pair<String,String>> Nodi=new ArrayList<Pair<String,String>>();
	Piece[] pieces;
	Piece selected;
	int height;
	int width;
	int moves; // number of moves the player has made
	int configuration;
	boolean hasWon;
	int [][]matrix;
	/**
	 * Basic constructor. Initializes height and width to standard klotski size.
	 * Initializes pieces to configuration 1
	 */
	public Board() {
		this.pieces = new Piece[10];
		this.configuration = 1;

		// initialize all pieces to configuration 1, set moves to 0, set
		// selectedPiece to null, and set hasWon to false
		reset();

		this.height = 5;
		this.width = 4;
		matrix=new int[height][width];
		initMatrix();
		setMatrix();
		setInstance();
	}

	/**
	 * Custom constructor that uses a custom array of pieces
	 * @param pieces the custom array of pieces that this board holds
	 */
	public Board(Piece[] pieces) {
		this.pieces = pieces;
		this.height = 5;
		this.width = 4;
		this.moves = 0;
		this.configuration = 1;
		this.hasWon = false;
		this.selected = null;
	}

	/**
	 * Sets configuration to the given number
	 * @param number input to set configuration to
	 */
	public void setConfig(int number) {
		this.configuration = number;
	}

	/**
	 * Reads in a set a lines representing a board state and sets the pieces of
	 * this board to match it
	 * @param lines a List of lines with the first being the number of moves,
	 * and the rest representing the x, y, w, and h of pieces
	 * @return true if able to successfully read in from file, false otherwise
	 */
	public boolean setPieces(List<String> lines) {
		int i;
		String[] tokens;
		if (lines.size() < 1 || lines.size() > this.width * this.height) {
			throw new IllegalArgumentException("Illegal list of lines");
		}
		this.moves = Integer.parseInt(lines.get(0).trim());
		pieces = new Piece[lines.size() - 1];
		for (i = 1; i < lines.size(); ++i) {
			tokens = lines.get(i).trim().split("\\s+");
			pieces[i - 1] = new Piece(Integer.parseInt(tokens[0]),
					Integer.parseInt(tokens[1]),
					Integer.parseInt(tokens[2]),
					Integer.parseInt(tokens[3]),Integer.parseInt(tokens[4]));
		}
		return true;
	}

	/**
	 * hasWon getter
	 * @return whether the play has won
	 */
	public boolean checkWin() { return hasWon; }

	/**
	 * move getter
	 * @return the current number of moves
	 */
	public int getMoves() { return moves; }

	/**
	 * selectedPiece getter
	 * @return this board's selectedPiece
	 */
	public Piece getSelectedPiece() { return selected; }

	/**
	 * width getter
	 * @return this board's width
	 */
	public int getWidth() { return width; }

	/**
	 * height getter
	 * @return this board's height
	 */
	public int getHeight() { return height; }

	/**
	 * pieces getter
	 * @return this board's pieces
	 */
	public Piece[] getPieces() { return pieces; }

	/**
	 * selects the piece at the given x and y coordinates
	 * @param x the x coordinate of the point in the piece you want to select
	 * @param y the y coordinate of the point in the piece you want to select
	 * @return true if a piece was selected, false otherwise
	 */
	public boolean selectPiece(int x, int y) {
		for (Piece p : pieces) {
			if (p.containsPoint(x, y)) {
				selected = p;
				return true;
			}
		}

		// if we get here then they clicked on an empty square, so deselect
		// the piece
		selected = null;
		return false;
	}

	/**
	 * Checks whether there is a piece occupying a given point
	 * @param x the x coordinate of the point to check
	 * @param y the y coordinate of the point to check
	 * @return true if the point is occupied
	 */
	public boolean isOccupied(int x, int y) {
		for (Piece p : pieces) {
			if (p.containsPoint(x, y)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Tries to move the selected piece in the given direction
	 * @param direction 0=up, 1=right, 2=down, 3=left
	 * @return true if the move was successful, false otherwise
	 */
	public boolean movePiece(int direction) {
		int i;

		// if there's no selected piece we can't move, so just return false
		if (selected == null) {
			return false;
		}

		// check for a win
		if (selected == pieces[0] && selected.x == 1 &&
				selected.y == 3 && direction == 2) {
			hasWon = true;
			return true;
		}

		if (direction == 0) {
			// up
			if (selected.y == 0) return false;
			for (i = selected.x; i < selected.x + selected.w; ++i) {
				if (isOccupied(i, selected.y - 1)) {
					// there's a piece blocking this one
					return false;
				}
			}
		} else if (direction == 1) {
			// right
			if (selected.x + selected.w == width) return false;
			for (i = selected.y; i < selected.y + selected.h; ++i) {
				if (isOccupied(selected.x + selected.w, i)) {
					// there's a piece blocking this one
					return false;
				}
			}
		} else if (direction == 2) {
			// down
			if (selected.y + selected.h == height) return false;
			for (i = selected.x; i < selected.x + selected.w; ++i) {
				if (isOccupied(i, selected.y + selected.h)) {
					// there's a piece blocking this one
					return false;
				}
			}
		} else if (direction == 3) {
			// left
			if (selected.x == 0) return false;
			for (i = selected.y; i < selected.y + selected.h; ++i) {
				if (isOccupied(selected.x - 1, i)) {
					// there's a piece blocking this one
					return false;
				}
			}
		} else {
			throw new IllegalArgumentException("direction must be 0..3");
		}

		// if we've gotten here it means we're clear to move the selected piece
		selected.move(direction);
		++moves;
		setMatrix();
		setInstance();
		printMatrix();

		return true;
	}

	/*
	 * Sets all pieces to their original position for the current configuration,
	 * sets moves to 0, sets selectedPiece to null, and sets hasWon to false
	 */
	public void reset() {
		pieces = new Piece[10];
		if (configuration == 1) {
			pieces[0] = new Piece(1, 1, 0, 2, 2);
			pieces[1] = new Piece(2, 0, 0, 1, 2);
			pieces[2] = new Piece(3, 3, 0, 1, 2);
			pieces[3] = new Piece(4, 0, 2, 1, 2);
			pieces[4] = new Piece(5, 1, 2, 1, 1);
			pieces[5] = new Piece(6, 2, 2, 1, 1);
			pieces[6] = new Piece(7, 3, 2, 1, 2);
			pieces[7] = new Piece(8, 1, 3, 1, 1);
			pieces[8] = new Piece(9, 2, 3, 1, 1);
			pieces[9] = new Piece(10, 1, 4, 2, 1);
		} else if (configuration == 2) {
			pieces[0] = new Piece(1, 1, 0, 2, 2);
			pieces[1] = new Piece(2, 0, 0, 1, 1);
			pieces[2] = new Piece(3, 3, 0, 1, 1);
			pieces[3] = new Piece(4, 0, 1, 1, 2);
			pieces[4] = new Piece(5, 3, 1, 1, 2);
			pieces[5] = new Piece(6, 1, 2, 1, 2);
			pieces[6] = new Piece(7, 0, 3, 1, 1);
			pieces[7] = new Piece(8, 3, 3, 1, 1);
			pieces[8] = new Piece(9, 0, 4, 2, 1);
			pieces[9] = new Piece(10, 2, 4, 2, 1);
		} else if (configuration == 3) {
			pieces[0] = new Piece(1, 2, 1, 2, 2);
			pieces[1] = new Piece(2, 0, 0, 1, 2);
			pieces[2] = new Piece(3, 1, 0, 1, 1);
			pieces[3] = new Piece(4, 2, 0, 1, 1);
			pieces[4] = new Piece(5, 3, 0, 1, 1);
			pieces[5] = new Piece(6, 1, 1, 1, 2);
			pieces[6] = new Piece(7, 0, 2, 1, 2);
			pieces[7] = new Piece(8, 1, 3, 2, 1);
			pieces[8] = new Piece(9, 3, 3, 1, 1);
			pieces[9] = new Piece(10, 2, 4, 2, 1);
		} else if (configuration == 4) {
			pieces[0] = new Piece(1, 1, 0, 2, 2);
			pieces[1] = new Piece(2, 0, 0, 1, 2);
			pieces[2] = new Piece(3, 3, 0, 1, 2);
			pieces[3] = new Piece(4, 0, 2, 1, 2);
			pieces[4] = new Piece(5, 1, 2, 2, 1);
			pieces[5] = new Piece(6, 3, 2, 1, 2);
			pieces[6] = new Piece(7, 1, 3, 1, 1);
			pieces[7] = new Piece(8, 2, 3, 1, 1);
			pieces[8] = new Piece(9, 0, 4, 1, 1);
			pieces[9] = new Piece(10, 3, 4, 1, 1);
		}

		moves = 0;
		selected = null;
		hasWon = false;

	}

	/**
	 * Converts the entire board to a string, for saving
	 * @return the String version of this board
	 */
	@Override
	public String toString() {
		String out = Integer.toString(moves) + "\n";
		for (Piece p : pieces) {
			out = out.concat(p.toString() + "\n");
		}
		return out;
	}

	public void setMatrix() {
		initMatrix();
		int id=0;
		System.out.println();
		for (Piece p : pieces) {
			for(int i=p.y;i<p.y+p.h;i++) {
				for(int j=p.x;j<p.x+p.w;j++) {
					matrix[i][j]=1+id;
				}
			}
			id++;
		}
	}

	public void printMatrix() {
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				System.out.print(matrix[i][j] + "  ");
			}
			System.out.println("\n");
		}
	}

	private void initMatrix() {
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++)
				matrix[i][j]=0;		
	}
	//blocco 1x1= tipo 0,1x2=tipo 1,2x1=tipo 2, 2x2= tipo 3
	public static int getBlockType(Piece p) {
		int out=0;
		if(p.w==1 && p.h==1) 
			out= 0;
		if(p.w==1 && p.h==2) 
			out= 1;
		if(p.w==2 && p.h==1) 
			out= 2;
		if(p.w==2 && p.h==2) 
			out= 3;
		return out;

	}
	private void setInstance() {
		Path path = Paths.get("encodings/SlidingBlocks-instance");
		String instance="";
		for(int i= 0; i<height;i++) {
			for(int j= 0; j<width;j++) {
				if(matrix[i][j]==0) instance=(instance + new String("empty("+i+","+j+").\n"));
			}
		}
		int id=0;


		for (Piece p : pieces) {
			int type=getBlockType(p);
			instance=(instance + new String("blocco("+type+", "+id+", "+p.y+", "+p.x+", "+p.w+", "+p.h+").\n"));
			id++;
		}

		try {
			Files.write(path, instance.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//da generalizzare

		program.addFilesPath(encodingResource);
		program.addFilesPath(instanceResource);
		handler.addProgram(program);
		Output o =  handler.startSync();

		AnswerSets answers = (AnswerSets) o;
		for(AnswerSet a:answers.getAnswersets()){
			//if(a.toString()=="canMove")
			//System.out.println("AS n.: " + ++n + ": " + a);
			String s2 = a.toString();
			String nextInstance="";
			StringTokenizer st = new StringTokenizer(s2);
			while (st.hasMoreTokens()) { //per ognuno di questi answerset il programma deve essere in grado di orlare il file next-step con l'istanza aggiornata della mossa, ad esempio se canMoveDown sposti gi� quella casella e 
				String temp=st.nextToken();//ne calcoli le rispettive conseguenze. Questo va fatto per ognuno di questi fatti. Il tutto deve essere ricorsivo e deve continuare finch� non trova la condizione di stop.

				if(temp.contains("canMoveDown")||temp.contains("canMoveRight")||temp.toString().contains("canMoveUP")||temp.toString().contains("canMoveLeft")) {
					temp = temp.replace(temp.substring(temp.length()-1), "");
					listaMosse.add(temp);
					nextInstance=(nextInstance +temp+".\n");
				}
				Path path2 = Paths.get("encodings\\Sliding-blocks-Next-Step");

				try {
					Files.write(path2, nextInstance.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		for(String t:listaMosse) {
			Pair<String,String> current=new Pair(t+".\n",instance);
			Nodi.add(current);
		}
		for(Pair<String,String> pair: Nodi) {
			System.out.println("Pair " + pair.getKey()+ pair.getValue());
		}
		solve();
	}

	public void moveById(String s) {
		int id=0;
		int direction = 0;
		if(s.contains("canMoveUp")) direction=0;
		else if(s.contains("canMoveRight")) direction=1;
		else if(s.contains("canMoveDown")) direction=2;
		else if(s.contains("canMoveLeft")) direction=3;
		s = s.replaceAll("\\D+",""); // rimuove tutti icaratteri
		id=Integer.parseInt(s);
		for(Piece p: pieces) {
			if(p.getId()==id) {
				p.move(direction);
			}
		}
	}
	public void solve() {
		File file = new File("encodings\\Sliding-blocks-Next-Step"); 

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		String st;
		try {
			while ((st = br.readLine()) != null) {
			System.out.println("Invocata su "+st);
				//moveById(st);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
