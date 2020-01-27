package klotski.controller;

import klotski.model.Board;
import klotski.view.KlotskiApp;

public class ResetPuzzleController {
	final KlotskiApp app;
	final Board b;

	public ResetPuzzleController(KlotskiApp app, Board b) {
		this.app = app;
		this.b = b;
	}

	public void reset() {
		b.reset();
		app.getMovesCounter().setText(Integer.toString(b.getMoves()));
		app.getPuzzleView().refresh();
	}
}
