package klotski.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import klotski.model.Board;

public class SaveController {
	final Board b;
	final Path p;

	public SaveController(Board b, Path p) {
		this.b = b;
		this.p = p;
	}

	public boolean save() {
	    String s = b.toString();
	    byte data[] = s.getBytes();

	    try (OutputStream out = new BufferedOutputStream(
	    		Files.newOutputStream(p))) {
	      out.write(data, 0, data.length);
	    } catch (IOException e) {
	      System.err.println(e);
	      return false;
	    }
	    return true;
	}
}
