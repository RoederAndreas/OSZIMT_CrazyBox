package controller;

import entities.Case;
import javafx.collections.ObservableList;
import service.IDatenhaltung;

public class Fachkonzept1 implements IFachkonzept{

	private IDatenhaltung datenhaltung;
	
	public Fachkonzept1() {}
	
	@Override
	public void createDBConnection() {
		datenhaltung.dbConnection();
	}
	
	@Override
	public void createCase(int payload, String name) {
		Case myCase = new Case(payload, name);
		datenhaltung.createCase(myCase);
	}

	@Override
	public void editCase(int id, int payload, String name) {
		Case selectedCase = new Case(id, payload, name);
		datenhaltung.editCase(selectedCase);
	}

	@Override
	public void deleteCase(int id) {
		datenhaltung.deleteCase(id);
	}

	@Override
	public ObservableList<Case> showAllCases() {
		datenhaltung.dbConnection();
		return datenhaltung.getAllCases();
	}

	@Override
	public void setDatenhaltung(IDatenhaltung datenhaltung) {
		this.datenhaltung = datenhaltung;
	}
}
