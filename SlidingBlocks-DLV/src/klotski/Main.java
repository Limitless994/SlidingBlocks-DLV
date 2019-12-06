package klotski;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;
import klotski.model.Board;
import klotski.model.Piece;
import klotski.view.KlotskiApp;

public class Main {
	public static void main(String[] args) {
		Board b = new Board();
		KlotskiApp app = new KlotskiApp(b);
		app.setVisible(true);
	}
}
