package klotski;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import klotski.model.Board;
import klotski.view.KlotskiApp;

public class Main{

	public static void main(String[] args) {
				Board b = new Board(4);
				//livello
				KlotskiApp app = new KlotskiApp(b);
				app.setVisible(true);
	
	}
}