package controller;

import javafx.collections.*;
import model.Case;
import model.Item;
import service.IDatenhaltung;

public class Fachkonzept2 implements IFachkonzept {

	private IDatenhaltung datenhaltung;
	
	public Fachkonzept2() {}
	
	@Override
	public void createDBConnection() {
		datenhaltung.dbConnection();
	}

	@Override
	public void setDatenhaltung(IDatenhaltung datenhaltung) {
		this.datenhaltung = datenhaltung;
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
	public ObservableList<Item> findItemsFromCase(int id) {
		ObservableList<Item> list = datenhaltung.getItemFromCase(id);
		FXCollections.sort(list);
		return list;
	}

	@Override
	public ObservableList<Case> showAllCases() {
		ObservableList<Case> list = datenhaltung.getAllCases();
		FXCollections.sort(list);
		return list;
	}

	@Override
	public void createItem(String designation, int weight, String description, Case selectionCase) {
		Item item = new Item(designation, weight, description, selectionCase);
		datenhaltung.createItem(item);
	}

	@Override
	public void deleteItem(int id) {
		datenhaltung.deleteItem(id);
	}

	@Override
	public void editItem(int id, String designation, int weight, String description, Case selectionCase) {
		Item item = new Item(id, designation, weight, description, selectionCase);
		datenhaltung.editItem(item);
	}

	@Override
	public void setItemsToGround(int id) {
		datenhaltung.setItemsToGround(id);
	}

	@Override
	public int getItemsWeight(int caseID) {
		return datenhaltung.getItemsWeight(caseID);
	}

}
