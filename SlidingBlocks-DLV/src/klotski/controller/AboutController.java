package klotski.controller;

import javax.swing.JOptionPane;

import klotski.view.KlotskiApp;

public class AboutController {
	final KlotskiApp app;

	public AboutController(KlotskiApp app) {
		this.app = app;
	}

	public void about() {        
		JOptionPane.showMessageDialog(app, 
			
			"Versione del gioco KLOTSKI con integrazione di DLV.\n" +
			"Sviluppato da Riccardo Mallamaci e Davide Sacco\n" +
			"per il corso di Intelligenza Artificiale del cdl di Informatica\n"+
			"              presso l'Università della Calabria.\n",

			"Sliding Blocks Geometry DLV",

			JOptionPane.INFORMATION_MESSAGE);
	}
}
