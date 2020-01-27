package klotski.controller;

import java.awt.event.MouseEvent;

import klotski.model.Board;
import klotski.view.KlotskiApp;

public class SelectPieceController {
	final KlotskiApp app;
	final Board b;
	

	public SelectPieceController(KlotskiApp app, Board b) {
		this.app = app;
		this.b = b;
	}
	

	public boolean select(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int xCoord = e.getX() / app.getPuzzleView().getSquareSize();
			int yCoord = e.getY() / app.getPuzzleView().getSquareSize();
						
			b.selectPiece(xCoord, yCoord);
			
			app.getPuzzleView().refresh();
			
			return true;
		}
		
		return false;
	}
}
