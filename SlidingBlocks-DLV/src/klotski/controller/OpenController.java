package klotski.controller;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import klotski.model.Board;
import klotski.view.KlotskiApp;

public class OpenController {
	KlotskiApp app;
	Board b;
	final Path p;

	public OpenController(KlotskiApp app, Board b, Path p) {
		this.app = app;
		this.b = b;
		this.p = p;
	}

	public boolean open() {
		Charset charset = Charset.forName("UTF-8");
		try {
			List<String> lines = Files.readAllLines(p, charset);
			b.setPieces(lines);
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}
		app.getPuzzleView().refresh();
		app.getMovesCounter().setText(Integer.toString(b.getMoves()));
		return true;
	}
}
