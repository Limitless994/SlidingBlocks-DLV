package klotski;

import klotski.model.Board;
import klotski.view.KlotskiApp;

public class SlidingBlocksGeometryDLV{

	public static void main(String[] args) {
		Board b = new Board(1);
		KlotskiApp app = new KlotskiApp(b);
		app.setVisible(true);
	}
}