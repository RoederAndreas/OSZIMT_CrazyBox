package view;

import controller.Fachkonzept1;
import service.Datenhaltung1;
import service.Datenhaltung2;

public class Main{
	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.setIFachkonzept(new Fachkonzept1());
		gui.setIDatenhaltung(new Datenhaltung2());
		gui.startFXMLGUI(args);
	}	
}
