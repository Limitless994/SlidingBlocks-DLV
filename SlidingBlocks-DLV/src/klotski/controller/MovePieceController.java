package klotski.controller;

import klotski.model.Board;
import klotski.view.KlotskiApp;

public class MovePieceController {
	final KlotskiApp app;
	final Board b;
	
	public MovePieceController(KlotskiApp app, Board b) {
		this.app = app;
		this.b = b;
	}
	
	public boolean move(int direction) {
		if (b.movePiece(direction)) {
			app.getMovesCounter().setText(Integer.toString(b.getMoves()));
			app.getPuzzleView().refresh();
			return true;
		}
			return false;
	}
}
