package klotski.model;

public class Piece {
	int id;
	int x; 
	int y; 
	int w;
	int h; 


	public Piece(int id, int w, int h, int y, int x) {
		if (x < 0 || y < 0 || w < 1 || h < 1)
			throw new IllegalArgumentException("Piece values must be positive");
		this.x = x;
		this.y = y;
		this.w = w;
		this.h= h;
		this.id=id;
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void move(int direction) {
		if (direction == 0) // up
			this.y--;
		else if (direction == 1) // right
			this.x++;
		else if (direction == 2) // down
			this.y++;
		else if (direction == 3) // left
			this.x--;
		else
			throw new IllegalArgumentException("direction must be 0..3");
	}
	
	public boolean containsPoint(int x, int y) {
		return (x >= this.x && y >= this.y &&
				x < (this.x + this.w) && y < (this.y + this.h));
	}

	public int[] getDims() {
		return new int[] {this.x, this.y, this.w, this.h};
	}

	public String toString() {
		String out = "";
		out = out.concat(Integer.toString(x) + " ")
				.concat(Integer.toString(y) + " ")
				.concat(Integer.toString(w) + " ")
				.concat(Integer.toString(h));
		return out;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
