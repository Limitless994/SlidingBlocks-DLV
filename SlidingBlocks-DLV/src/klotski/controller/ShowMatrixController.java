package klotski.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import klotski.model.Board;

public class ShowMatrixController {
	final Board b;
	
	/**
	 * Basic constructor
	 * @param app the view application
	 */
	public ShowMatrixController(Board b) {
		this.b = b;
	}
	
	/**
	 * Print the board to textline
	 */
	public void show() {
		
	    System.out.println("R C W H\nMosse:"+b);
	}
}
