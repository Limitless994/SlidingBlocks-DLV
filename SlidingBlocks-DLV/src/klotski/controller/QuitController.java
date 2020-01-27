package klotski.controller;

import javax.swing.JOptionPane;

import klotski.view.KlotskiApp;

	
public class QuitController {

	public boolean confirm(KlotskiApp app) {
		return JOptionPane.showOptionDialog (app, 
				"Vuoi uscire?", "SLIDING BLOCKS DLV - Conferma?",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
				null, null) == JOptionPane.OK_OPTION;	
	}
}
