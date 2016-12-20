package view;

import controller.Fachkonzept1;
import controller.Fachkonzept2;
import service.Datenhaltung1;
import service.Datenhaltung2;

public class Main{
	public static void main(String[] args) {
		TUI tui = new TUI(new Fachkonzept1(), new Datenhaltung1());

//		Gui gui = new Gui();
//		gui.setIFachkonzept(new Fachkonzept1());
//		gui.setIDatenhaltung(new Datenhaltung2());
//		gui.startFXMLGUI(args);
	}
}
