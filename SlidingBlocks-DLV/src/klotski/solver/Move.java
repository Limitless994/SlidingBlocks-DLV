package klotski.solver;
class Move {
	int x;
	int y;
	Block block;
	
	public Move(int x, int y, Block block){
		this.x = x;
		this.y = y;
		this.block = block;
	}
}