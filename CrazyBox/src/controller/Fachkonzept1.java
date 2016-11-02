package controller;

import javafx.collections.ObservableList;
import model.Case;
import model.Item;
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
		return datenhaltung.getAllCases();
	}

	@Override
	public ObservableList<Item> findItemsFromCase(int id) {
		return datenhaltung.getItemFromCase(id);
	}

	@Override
	public void createItem(String designation, int weight, String description, Case selectionCase) {
		Item item = new Item(designation, weight, description, selectionCase);
		datenhaltung.createItem(item);
	}
	
	@Override
	public void setDatenhaltung(IDatenhaltung datenhaltung) {
		this.datenhaltung = datenhaltung;
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
