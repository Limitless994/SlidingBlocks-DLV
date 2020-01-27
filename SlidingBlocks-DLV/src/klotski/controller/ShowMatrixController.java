package klotski.controller;


import klotski.model.Board;

public class ShowMatrixController {
	final Board b;

	public ShowMatrixController(Board b) {
		this.b = b;
	}

	public void show() {
		
	    System.out.println("R C W H\nMosse:"+b);
	}
}
