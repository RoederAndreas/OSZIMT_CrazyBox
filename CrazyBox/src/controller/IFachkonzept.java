package controller;

import javafx.collections.ObservableList;
import model.Case;
import model.Item;
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
	void deleteItem(int id);
	void editItem(int id, String designation, int weight, String description, Case selectionCase);
	
	void setItemsToGround(int id);
	int getItemsWeight(int caseID);
}
