package klotski.solver;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

/**
 * @author Alex Salerno
 * @author Chris Parsoneault
 * @version 0.1
 *
 */

public class KlotskiPuzzle {
	static final String EMPTY = "0";		//an empty space in the grid
	static final String SOLVED_CHAR = "J";	//Block name that meets victory condition
	static final int GRID_COLUMN = 5;
	static final int GRID_ROW = 4;
	static final String[] BLOCK_NAMES = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	
	static final String DEFAULT_CONFIG = "AJJCAJJCBEEDBGHDF00I";	//116 moves to win
	//static final String DEFAULT_CONFIG = "HEEGA0C0AFCIBDJJBDJJ";	//7 moves to win
	//static final String DEFAULT_CONFIG = "0B0CABDCAHDFJJEEJJIG";	//9 moves to win
//	static final String DEFAULT_CONFIG = "0AB0DABCDJJCIJJFHEEG";	//25 moves to win
	
	String[][] grid = new String[GRID_COLUMN][GRID_ROW];
	Map<String, Block> blocks = new Hashtable<String, Block>();
	
	//Track grid configurations of each move
	Stack<String> grids = new Stack<String>();						
	//Track each move up until current configuration
	Stack<Move> moves = new Stack<Move>();
	
	//CONSTRUCTOR
	public KlotskiPuzzle(){
		init(DEFAULT_CONFIG);
	}
	
	public KlotskiPuzzle(String code){
		init(code);
	}
	
	private void init(String code){
		initBlocks();
		codeToGrid(code);
		grids.push(getGridCode());
	}
	
	/*
	 * @param x - x-coord to move to
	 * @param y - y-coord to move to
	 * @param b - Block being moved
	 */
	public Boolean move(int x, int y, String block){
		if(block.isEmpty() || !blocks.containsKey(block)) return false;
		Block b = blocks.get(block);
		if(!isValidMove(x,y,b)) return false;
		else {
			replaceBlock(b.name,EMPTY);
			insertBlock(x,y,b);
			grids.push(getGridCode());
			moves.push(new Move(x,y,b));
		}
		return true;
	}
	
	public Boolean isSolved(){
		if(grid[4][1].equals(SOLVED_CHAR) && grid[4][2].equals(SOLVED_CHAR)){
			return true;
		}
		return false;
	}
	
	//Prints out the puzzles current state
	public void printPuzzle(){
		System.out.println("    0 1 2 3 4 ");
		System.out.println("   -----------");
		for(int j=0; j<GRID_ROW;j++){
			System.out.print(j + " | ");
			for(int i=0; i<GRID_COLUMN;i++){
				System.out.print(grid[i][j] + " ");
			}
			if(j==1 || j==2) System.out.print("+");
			else System.out.print("|");
			System.out.println();
		}
		System.out.println("   -----------");
	}
	
	public String getGrid(int x, int y){
		if(x < 0 || x >= GRID_COLUMN) return "";
		if(y < 0 || y >= GRID_ROW) return "";
		return grid[x][y];
	}
	
	//TODO make more efficient, use Huffman encoding
	public String getGridCode(){
		String code = "";
		for(int i=0;i<GRID_COLUMN;i++){
			for(int j=0;j<GRID_ROW;j++){
				code += grid[i][j];
			}
		}
		return code;
	}
	
	public Stack<Move> getMoves(){
		return moves;
	}
	
	public Block getBlock(String block){
		if(!blocks.containsKey(block)) return null;
		return blocks.get(block);
	}
	
	public Boolean undo(){
		if(grids.size() < 2) return false;
		grids.pop();
		moves.pop();
		codeToGrid(grids.peek());
		return true;
	}
	
	public void codeToGrid(String code){
		int s = 0;
		int max = GRID_COLUMN * GRID_ROW;
		for(int i=0;i<GRID_COLUMN;i++){
			for(int j=0;j<GRID_ROW;j++){
				if(s>code.length() || s>=max) break;
				grid[i][j] = Character.toString(code.charAt(s));
				s++;
			}
		}
	}
	
	public void printMoves(){
		while(!moves.isEmpty()){
			Move m = moves.pop();
			System.out.println("Move " + m.block.name + " to (" + m.x + "," + m.y + ")");
		}
	}
	
	/*	=================================================
	 * 	Private Functions
	 *  =================================================
	 */ 
	
	//Initializes blocks to default sizes
	private void initBlocks(){
		//new Block(Height,Width,Name)
		blocks.put("A",new Block(1,1,2,"A"));
		blocks.put("B",new Block(2,1,2,"B"));
		blocks.put("C",new Block(3,1,2,"C"));
		blocks.put("D",new Block(4,1,2,"D"));
		blocks.put("E",new Block(5,2,1,"E"));
		blocks.put("F",new Block(6,1,1,"F"));
		blocks.put("G",new Block(7,1,1,"G"));
		blocks.put("H",new Block(8,1,1,"H"));
		blocks.put("I",new Block(9,1,1,"I"));
		blocks.put("J",new Block(10,2,2,"J"));
	}
	
	/* If block b will collide with any block return true, else false
	 * @param x - x-coord to move to
	 * @param y - y-coord to move to
	 * @param b - Block being moved
	 */
	private Boolean isCollision(int x, int y, Block b){
		if(grid[x][y].equals(EMPTY) || grid[x][y].equals(b.name)){
			//check for width collision
			for(int i=1;i<b.colonna;i++){
				if(!grid[x+i][y].equals(EMPTY) && !grid[x+i][y].equals(b.name)){
					return true;
				}
			}
			//check for height collision
			for(int i=1;i<b.riga;i++){
				if(!grid[x][y+i].equals(EMPTY) && !grid[x][y+i].equals(b.name)){
					return true;
				}
			}
			//check for inner
			for(int i=1;i<b.colonna;i++){
				for(int j=1;j<b.riga;j++){
					if(!grid[x+i][y+j].equals(EMPTY) && !grid[x+i][y+j].equals(b.name)){
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}
	
	private Boolean isValidMove(int x, int y, Block b){
		Move c = getBlockPos(b);
		if(x+(b.colonna-1) >= GRID_COLUMN || y+(b.riga-1) >= GRID_ROW) return false;
		if(isCollision(x,y,b)) return false;
		if(x != c.x && y != c.y) return false;
		if(Math.abs(x-c.x) > 1 || Math.abs(y-c.y) > 1) return false;
		return true; 
	}
	
	//returns move object with current position of block b
	public Move getBlockPos(Block b){
		if(b != null && blocks.containsKey(b.name)){
			Move c = new Move(0,0,b);
			for(int i=0;i<GRID_COLUMN;i++){
				for(int j=0;j<GRID_ROW;j++){
					if(grid[i][j].equals(b.name)){
						c.x = i;
						c.y = j;
						return c;
					}
				}
			}
		}
		return null;
	}
	
	/*
	 * Replaces Block B1 with B2
	 */
	private void replaceBlock(String b1, String b2){
		for(int i=0;i<GRID_COLUMN;i++){
			for(int j=0;j<GRID_ROW;j++){
				if(grid[i][j].equals(b1)){
					grid[i][j] = b2;
				}
			}
		}
	}
	
	private void insertBlock(int x, int y, Block b){
		for(int i=x;i<x+b.colonna;i++){
			for(int j=y;j<y+b.riga;j++){
				grid[i][j] = b.name;
			}
		}
	}
}