package klotski;

import klotski.model.Board;
import klotski.view.KlotskiApp;

public class Main {
	public static void main(String[] args) {
		Board b = new Board(3);
		KlotskiApp app = new KlotskiApp(b);
		app.setVisible(true);
	}
}
