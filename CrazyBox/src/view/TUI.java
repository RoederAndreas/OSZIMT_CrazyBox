package view;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import controller.IFachkonzept;
import javafx.collections.ObservableList;
import model.Case;
import model.Item;
import service.IDatenhaltung;

public class TUI {

	private static IFachkonzept fachkonzept;
	private static IDatenhaltung datenhaltung;
	
	private InputStreamReader scanner;
	private BufferedReader br;
	
	public TUI(IFachkonzept fachkonzept, IDatenhaltung datenhaltung){
		setIFachkonzept(fachkonzept);
		datenhaltung.dbConnection();
		this.fachkonzept.setDatenhaltung(datenhaltung);
		setIDatenhaltung(datenhaltung);
		startCMD();
	}
	
	public void setIFachkonzept(IFachkonzept fachkonzept){
		this.fachkonzept = fachkonzept;
	}

	public IFachkonzept getIFachkonzept(){
		return fachkonzept;
	}

	public void setIDatenhaltung(IDatenhaltung datenhaltung){
		this.datenhaltung = datenhaltung;
	}

	public IDatenhaltung getIDatenhaltung(){
		return datenhaltung;
	}
	
	private void startCMD() {
		System.out.println("1) Kiste erstellen");
		System.out.println("2) Kisten anzeigen");
		
		scanner = new InputStreamReader(System.in);
		br = new BufferedReader(scanner);
		String eingabe = getUserInput("Welche Aktion möchten Sie ausführen? [1-4]: ");
		switch (eingabe) {
        case "1":  
    		createCase();
            break;
        case "2":  
    		showCases();
            break;
        default:
        	System.out.println("Unbekannt");
		}
		System.out.flush();
	}
	
	private void showCases() {
		ObservableList<Case> cases = fachkonzept.showAllCases();
		int i = 1;
		for (Case current : cases) {
			System.out.println(i + ") " + current.getName());
			i++;
		}
		System.out.println(i + ") Zurück");
		int eingabe = Integer.parseInt(getUserInput(""));
		if (eingabe == i) {
			startCMD();
		} else {
			openCase(cases.get(eingabe - 1));
		}
	}
	
	private void openCase(Case box) {
		ObservableList<Item> items = fachkonzept.findItemsFromCase(box.getId());
		int i = 1;
		for (Item item : items) {
			System.out.println("");
			System.out.println(i + ") BEZEICHNUNG: " + item.getDesignation());
			System.out.println("BESCHREIBUNG: " + item.getDescription());
			System.out.println("GEWICHT: " + item.getWeight());
			i++;
		}
		System.out.println("");
		int addItem = i++;
		int deleteCase = i++;
		int back = i++;
		System.out.println(addItem + ") GEGENSTAND HINZUFÜGEN");
		System.out.println(deleteCase + ") KISTE LÖSCHEN");
		System.out.println(back + ") ZURÜCK");
		
		System.out.println("Zum Bearbeiten die Nummer des Gegenstands eingeben");
		//System.out.println("oder \"X\" zum Abbrechen:");
		int eingabe = Integer.parseInt(getUserInput("Auswahl: "));
		
		if (eingabe == addItem) createItem(box);
		else if (eingabe == deleteCase) deleteCase(box);
		else if (eingabe == back) startCMD();
		else editItem(items.get(eingabe - 1));
	}
	
	private void editItem(Item item) {
		String loeschen = getUserInput("Soll dieser Gegenstand gelöscht werden? (Y/N)");
		if (loeschen.equals("Y") || loeschen.equals("y")) {
			fachkonzept.deleteItem(item.getId());
			openCase(item.getSelectionCase());
			return;
		}
		String newName = getUserInput("Neuer Name: ");
		String newDesc = getUserInput("Neue Beschreibung: ");
		int newWeight = Integer.parseInt(getUserInput("Neues Gewicht: "));
		fachkonzept.editItem(item.getId(), newName, newWeight, newDesc, item.getSelectionCase());
		openCase(item.getSelectionCase());
	}
	
	private String getUserInput(String msg) {
		try {
			System.out.println(msg);
			return br.readLine();
		} catch (IOException e) {
			System.err.println("Fehler bei der Eingabe");
			e.printStackTrace();
			return null;
		}
	}
	
	private void createCase(){
		try {
			System.out.println("KISTE ERSTELLEN");
			String name = getUserInput("Bitte Namen der Kiste eingeben:");
			String capacity = getUserInput("Traglast der Kiste:");
			fachkonzept.createCase(Integer.parseInt(capacity), name);
			startCMD();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void deleteCase(Case box) {
		String loeschen = getUserInput("Kiste " + box.getName() + " wirklich löschen? (Y/N)");
		if (loeschen.equals("Y") || loeschen.equals("y")) {
			fachkonzept.deleteCase(box.getId());
			showCases();
		} else openCase(box);
	}
	
	private void createItem(Case selectionCase){
		System.out.println("GEGENSTAND ERSTELLEN");
		String designation = getUserInput("Bezeichnung: ");
		int weight = Integer.parseInt(getUserInput("Gewicht: "));
		String description = getUserInput("Beschreibung: ");
		fachkonzept.createItem(designation, weight, description, selectionCase);
		openCase(selectionCase);
	}
}
