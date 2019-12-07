package klotski.solver;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * @author Alex Salerno
 * @author John Stoddard
 * @author Arron McCarter
 * @author Chris Parsoneault
 * @version 0.1
 *
 */

public class KlotskiSolver {
	
	KlotskiPuzzle puzzle;
	Map<String, String> tree = new Hashtable<String, String>();
	
	public KlotskiSolver(KlotskiPuzzle puzzle){
			this.puzzle = puzzle;
	}
	
	public void solve(){
		solve(false);
	}
	
	public void solve(Boolean verbose){
    	long start = Calendar.getInstance().getTimeInMillis();
    	
		System.out.println("Solving puzzle...");
		
		//compute solution
		String solution = findValidPath(puzzle.getGridCode(),verbose);
		printSolution(solution);
		
		//calculate duration to solve
		long end = Calendar.getInstance().getTimeInMillis();
		long duration = (end - start)/1000;
		long durationExt = (end - start)%1000;
		
		System.out.println("Duration: " + duration + "." + durationExt + "s");
	}
	
	//Root is the grids gridCode
	private String findValidPath(String rootCode, Boolean verbose){
		Queue<String> grids = new LinkedList<String>();
		Set<String> pastGrid = new HashSet<String>();
		int moveCount = 0;
		String current = "";
		
		grids.add(rootCode);
		pastGrid.add(rootCode);
		tree.put(rootCode, "");
		
		while(!grids.isEmpty()){
			current = grids.remove();
			
			if(isSolved(current)){
				if(verbose) System.out.println("Solution found in " + moveCount + " tries!");
				break;
			}
			String[] nextGrid = findAllMoves(current, verbose);
			for(String g : nextGrid){
				if(pastGrid.contains(g)) continue;
				if(verbose) System.out.println(moveCount + "\tAdding grid " + g + " to queue. ");
				//add nodes to current, set each node parent to current node
				grids.add(g);
				pastGrid.add(g);
				tree.put(g,current);
			}
			moveCount++;
		}
		return current;
	}
	
	private String[] findAllMoves(String gridCode,Boolean verbose){
		if(verbose) System.out.println("\tFinding all moves for " + gridCode);
		//String[] blocks = KlotskiPuzzle.BLOCK_NAMES;
		List<String> results = new ArrayList<String>();
		KlotskiPuzzle p = new KlotskiPuzzle(gridCode);
		
		//Iterate through each column
		for(int i=0; i<KlotskiPuzzle.GRID_WIDTH;i++){
			for(int j=0;j<KlotskiPuzzle.GRID_HEIGHT;j++){
				if(p.getGrid(i,j).equals(KlotskiPuzzle.EMPTY)){
					String moveBlock = "";
					Move blockPos = null;
					
					//look up, check if you can move it down
					moveBlock = p.getGrid(i, j-1);
					blockPos = p.getBlockPos(p.getBlock(moveBlock));
					if(blockPos != null && p.move(blockPos.x, blockPos.y+1,moveBlock)){
						results.add(p.getGridCode());
						p = new KlotskiPuzzle(gridCode);
					}
					
					//look left, check if you can move it right
					moveBlock = p.getGrid(i-1, j);
					blockPos = p.getBlockPos(p.getBlock(moveBlock));
					if(blockPos != null && p.move(blockPos.x+1, blockPos.y,moveBlock)){
						results.add(p.getGridCode());
						p = new KlotskiPuzzle(gridCode);
					}
					
					//look right, check if you can move it left
					moveBlock = p.getGrid(i+1, j);
					blockPos = p.getBlockPos(p.getBlock(moveBlock));
					if(blockPos != null && p.move(blockPos.x-1, blockPos.y,moveBlock)){
						results.add(p.getGridCode());
						p = new KlotskiPuzzle(gridCode);
					}
					
					//look down, check if you can move it up
					moveBlock = p.getGrid(i, j+1);
					blockPos = p.getBlockPos(p.getBlock(moveBlock));
					if(blockPos != null && p.move(blockPos.x, blockPos.y-1,moveBlock)){
						results.add(p.getGridCode());
						p = new KlotskiPuzzle(gridCode);
					}
				}
			}
		}
		
		String[] resultsArray = new String[ results.size() ];
		return results.toArray(resultsArray);
	}
	
	private Boolean isSolved(String gridCode){
		KlotskiPuzzle p = new KlotskiPuzzle(gridCode);
		return p.isSolved();
	}
	
	private void printSolution(String solution){
		Stack<String> reverse = new Stack<String>();
		KlotskiPuzzle puzzle;
		int moves = 0;
		
		reverse.push(solution);
		String current = tree.get(solution);
		
		while(!current.isEmpty()){
			reverse.push(current);
			current = tree.get(current);
		}
		while(!reverse.isEmpty()){
			System.out.println("Move:" + moves++);
			puzzle = new KlotskiPuzzle(reverse.pop());
			puzzle.printPuzzle();
		}
		System.out.println("Solution in " + (moves-1) + " moves!");
	}
}
