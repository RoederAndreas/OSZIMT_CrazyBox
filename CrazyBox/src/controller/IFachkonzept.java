package controller;

import entities.Case;
import entities.Item;
import javafx.collections.ObservableList;
import service.IDatenhaltung;

public interface IFachkonzept {

	void createDBConnection();
	void setDatenhaltung(IDatenhaltung datenhaltung);
	void createCase(int payload, String name);
	void editCase(int id, int payload, String name);
	void deleteCase(int id);
	ObservableList<Item> findItemsFromCase(int id);
	ObservableList<Case> showAllCases();
	
	void createItem(String designation, int weight, String description, Case selectionCase);
}
