package klotski.model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

//@Id("blocco")
public class Piece {
//	@Param(0)
	int id;
//	@Param(1)
	int x; // the x coordinate of the top left corner of the piece
//	@Param(2)
	int y; // the y coordinate of the top left corner of the piece
//	@Param(3)
	int w; // the horizontal width of the piece
//	@Param(4)
	int h; // the vertical height of the piece

	/**
	 * Basic constructor for initializing a piece
	 * @param x the x coordinate of the top left corner of the piece
	 * @param y the y coordinate of the top left corner of the piece
	 * @param w the horizontal width of the piece
	 * @param h the vertical height of the piece
	 */
	public Piece(int id,int x, int y, int w, int h) {
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

	/**
	 * moves a piece in the given direction on the board. Assumes the move is
	 * valid.
	 * @param direction 0=up, 1=right, 2=down, 3=left
	 */
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
	
	/**
	 * Checks whether this piece contains the given point
	 * @param x the x coordinate of the point being tested
	 * @param y the y coordinate of the point being tested
	 * @return true if this piece contains the given point, false otherwise
	 */
	public boolean containsPoint(int x, int y) {
		return (x >= this.x && y >= this.y &&
				x < (this.x + this.w) && y < (this.y + this.h));
	}
	
	/**
	 * Finds the x and y coordinates of the top left point in the piece, as
	 * well as the piece's width and height
	 * @return an int array with the left, top, width, and height values
	 */
	public int[] getDims() {
		return new int[] {this.x, this.y, this.w, this.h};
	}
	
	/**
	 * Converts this piece to a string, for saving
	 * @return the string representation of this piece
	 */
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
