package klotski.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.util.Pair;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;


public class Board {
	//path riccardo
	//	public String encodingResource="SlidingBlocks-DLV/encodings/SlidingBlocks-Rules";
	//	public String instanceResource="SlidingBlocks-DLV/encodings/level";
	//	public String output="SlidingBlocks-DLV/encodings/output";
	//	public Handler handler = new DesktopHandler(new DLVDesktopService("SlidingBlocks-DLV/lib/dlv2.win.x64_4"));

	//path Davide
	public String encodingResource="encodings/SlidingBlocks-Rules";
	public String instanceResource="encodings/level";
	public String output="encodings/output";
	public Handler handler = new DesktopHandler(new DLVDesktopService("lib/dlv2.win.x64_4"));

	//path Davide portatile
	////	public Handler handler = new DesktopHandler(new DLVDesktopService("lib/dlv2.win.32_4"));
	//		public String encodingResource="encodings/SlidingBlocks-Rules";
	//		public String instanceResource="encodings/level";
	//		public String output="encodings/output";

	public InputProgram  program = new ASPInputProgram();
	List<Pair<String,Integer>> moveSequence=new ArrayList<Pair<String,Integer>>();
	Piece[] pieces;
	Piece selected;
	int height;
	int width;
	int moves;
	int configuration;
	int nextDirection =0;
	int winX;
	int winY;
	boolean movibile = false;
	boolean firstTime=true;
	public int getNextDirection() {
		return nextDirection;
	}

	public void setNextDirection(int nextDirection) {
		this.nextDirection = nextDirection;
	}

	boolean hasWon;
	int [][]matrix;

	public Board(int level) {
		this.pieces = new Piece[10];
		this.configuration = level;
		instanceResource=instanceResource+configuration;

		reset();
		if(level==1) {
			this.height =2;
			this.width = 3;

		}else if(level==2) {
			this.height =3;
			this.width = 4;	

		}else if(level==3)  {
			this.height =3;
			this.width = 3;	
		}else if(level==4)  {
			this.height =5;
			this.width = 4;	
		}
		matrix=new int[height][width];
		moveSequence =new ArrayList<Pair<String,Integer>>();
		initMatrix();
		setMatrix();
		setInstance();
	}

	public Board(Piece[] pieces) {
		this.pieces = pieces;
		this.height = this.getHeight();
		this.width = this.getWidth();
		this.moves = 0;
		this.configuration = 1;
		this.hasWon = false;
		this.selected = null;
	}

	public void setConfig(int number) {
		this.configuration = number;
	}

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


	public boolean checkWin() { return hasWon; }


	public int getMoves() { return moves; }

	public Piece getSelectedPiece() { return selected; }


	public int getWidth() { return width; }

	public int getHeight() { return height; }

	public Piece[] getPieces() { return pieces; }

	public boolean selectPiece(int x, int y) {
		for (Piece p : pieces) {
			if (p.containsPoint(x, y)) {
				selected = p;
				return true;
			}
		}


		selected = null;
		return false;
	}

	public boolean isOccupied(int x, int y) {
		for (Piece p : pieces) {
			if (p.containsPoint(x, y)) {
				return true;
			}
		}

		return false;
	}

	public boolean movePiece(int direction) {
		int i;

		if (selected == null || hasWon==true) {
			return false;
		}

		if (direction == 0) {
			// up
			if (selected.y == 0) {
				movibile= false;
				return false;
			}
			for (i = selected.x; i < selected.x + selected.w; ++i) {
				if (isOccupied(i, selected.y - 1)) {
					movibile= false;
					return false;
				}
			}
		} else if (direction == 1) {
			if (selected.x + selected.w == width) {
				movibile= false;
				return false;
			}
			for (i = selected.y; i < selected.y + selected.h; ++i) {
				if (isOccupied(selected.x + selected.w, i)) {
					movibile= false;
					return false;
				}
			}
		} else if (direction == 2) {
			// down
			if (selected.y + selected.h == height) {
				movibile= false;
				return false;
			}
			for (i = selected.x; i < selected.x + selected.w; ++i) {
				if (isOccupied(i, selected.y + selected.h)) {
					movibile= false;
					return false;
				}
			}
		} else if (direction == 3) {
			// left
			if (selected.x == 0) {
				movibile= false;
				return false;
			}
			for (i = selected.y; i < selected.y + selected.h; ++i) {
				if (isOccupied(selected.x - 1, i)) {
					movibile= false;
					return false;
				}
			}
		} else {
			throw new IllegalArgumentException("direction must be 0..3");
		}
		movibile= true;
		selected.move(direction);
		++moves;
		setMatrix();
		if(checkVittoria()==true){
			hasWon = true;
		}

		return true;
	}

	public boolean checkVittoria(){


		if (selected == pieces[0] && selected.getX() ==winY && selected.getY()==winX) {
			System.out.println("Vinto");	
			return true;
		}

		return false;
	}

	public void reset() {
		if (configuration == 1) {
			winX = 1;
			winY = 0;
			pieces = new Piece[3];
			pieces[0] = new Piece(0, 2, 1, 0, 1); 
			pieces[1] = new Piece(1, 1, 1, 0, 0); 
			pieces[2] = new Piece(2, 1, 1, 1, 0); 


		} else if (configuration == 2) {
			winX = 2;
			winY = 0;
			pieces = new Piece[5];
			pieces[0] = new Piece(0, 2, 1, 0, 2);
			pieces[1] = new Piece(1, 2, 2, 1, 0);
			pieces[2] = new Piece(2, 1, 1, 2, 2);
			pieces[3] = new Piece(3, 1, 1, 0, 0);
			pieces[4] = new Piece(4, 1, 1, 0, 1);
		} else if (configuration == 3) {
			winX = 2;
			winY = 0;
			pieces = new Piece[6];
			pieces[0] = new Piece(0, 2, 1, 0, 1);
			pieces[1] = new Piece(1, 1, 1, 0, 0);
			pieces[2] = new Piece(2, 1, 1, 1, 1);
			pieces[3] = new Piece(3, 1, 1, 2, 2);
			pieces[4] = new Piece(4, 1, 1, 2, 1);
			pieces[5] = new Piece(5, 1, 1, 1, 2);

		}else if (configuration == 4) {
			winX = 3;
			winY = 1;
			pieces = new Piece[6];
			pieces[0] = new Piece(0, 2, 2, 0, 1);
			pieces[1] = new Piece(1, 1, 2, 0, 0);
			pieces[2] = new Piece(2, 1, 2, 0, 3);
			pieces[3] = new Piece(3, 1, 2, 2, 0);
			pieces[4] = new Piece(4, 1, 2, 2, 3);
			pieces[5] = new Piece(5, 2, 1, 2, 1);
		}

		moves = 0;
		selected = null;
		hasWon = false;

	}

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
				matrix[i][j]=9;		
	}
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

		Output o;
		AnswerSets answers=null;
		int contMosse=0;
		File outputFile= new File(output);
		program.addFilesPath(encodingResource);
		program.addFilesPath(output);
		handler.addProgram(program);
		do {
			System.out.println("MOSSE: " +contMosse);
			contMosse+=1;

			answers=null;
			String instance="";
			File file =new File(instanceResource); 
			Scanner sc = null;
			try {
				sc = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 

			while (sc.hasNextLine()) 
				instance=(instance+sc.nextLine()+"\n");

			instance=(instance+ new String("numMaxMosse(")+contMosse+").");
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(outputFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				writer.write(instance);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			o =  handler.startSync();
			answers = (AnswerSets) o;	



		}while(answers.getAnswersets().isEmpty());

		try{
			String s2 = answers.getAnswersets().get(0).toString();
			StringTokenizer st = new StringTokenizer(s2);
			while (st.hasMoreTokens()) { 
				String temp=st.nextToken();
				if(temp.contains("muovoGiu") ||temp.contains("muovoSu") ||temp.contains("muovoDestra")||temp.contains("muovoSinistra")) {
					fillAnswerList(temp);


				}
			}
		}catch(Exception e){
		}

		handler.removeProgram(program);
		handler.removeAll();
	}
	public void fillAnswerList(String temp) {


		temp = temp.replace(","," ");
		temp = temp.replace("}"," ");
		temp = temp.replace("{"," ");
		temp = temp.replace("muovoSinistra","L");
		temp = temp.replace("muovoDestra","R");
		temp = temp.replace("muovoSu","U");
		temp = temp.replace("muovoGiu","D");
		int contblank =0;
		for(int i =0;i<temp.length();i++) {
			if(contblank ==2) {
				contblank=0;
				String a =String.valueOf(temp.charAt(0));
				String b = String.valueOf(temp.charAt(i));
				Pair<String, Integer> pair = new Pair<>(a, Integer.parseInt(b));
				moveSequence.add(pair);		
				//				System.out.println(pair.getKey()+pair.getValue());
				break;
			}
			if(temp.charAt(i)==' ') {
				contblank++;
			}
		}


	}


	public void resetMoveSequence() {
		System.out.println("CHIAMO RESETMOVE");
		moveSequence =new ArrayList<Pair<String,Integer>>();
		selected= null;
		movibile = false;
		reset();
		nextDirection =0;
	}

	public void moveById(String s){ 
		int id=0;
		int direction = 0;
		s = s.replaceAll("\\D+","");
		id=Integer.parseInt(s);
		id/=10;
		direction=Integer.parseInt(s);
		direction-=id*10;
		id++;
		for(Piece p: this.pieces) {
			if(p.getId()==id)p.move(direction);
		}
	}

	public void addUserAction(String direction) {

		if(movibile==true) {
			Pair<String, Integer> pair = new Pair<>(direction, selected.getId());
			moveSequence.add(0, pair);
		}

	}

	public void moveBlock() {
		int nextBlock = moveSequence.get(0).getValue();  
		//		direction 0=up, 1=right, 2=down, 3=left
		if(moveSequence.get(0).getKey().equals("U")) {
			nextDirection =0;
			//System.out.println("Ho mosso sù");
		}
		else if(moveSequence.get(0).getKey().equals("D")) {
			nextDirection=2;
			//System.out.println("Ho mosso giù");
		}
		else if(moveSequence.get(0).getKey().equals("L")) {
			nextDirection=3;
			//System.out.println("Ho mosso sx");
		}
		else if(moveSequence.get(0).getKey().equals("R")) {
			nextDirection=1;
			//System.out.println("Ho mosso dx");
		}
		for(Piece p: pieces) {
			if( p.getId() ==  nextBlock){
				selected= p;
				moveSequence.remove(0);
				break;
			}
		}

	}


	public List<Pair<String, Integer>> getMoveSequence() {
		return moveSequence;
	}

	public void setMoveSequence(List<Pair<String, Integer>> moveSequence) {
		this.moveSequence = moveSequence;
	}

	public int getWinX() {
		return winX;
	}

	public void setWinX(int winX) {
		this.winX = winX;
	}

	public int getWinY() {
		return winY;
	}

	public void setWinY(int winY) {
		this.winY = winY;
	}

	public int getConfiguration() {
		return configuration;
	}

	public void setConfiguration(int configuration) {
		this.configuration = configuration;
	}

}
