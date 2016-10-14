package service;

import entities.Case;
import entities.Item;
import javafx.collections.ObservableList;

public interface IDatenhaltung {

	ObservableList<Case>  getAllCases();
	void deleteCase(int id);
	void editCase(Case editCase);
	void dbConnection();
	void createCase(Case createCase);
	Case findCase(int id);

	ObservableList<Item> getItemFromCase(int id);
	void createItem(Item item);
}
