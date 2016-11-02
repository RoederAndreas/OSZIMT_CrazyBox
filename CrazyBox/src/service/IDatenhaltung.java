package service;

import javafx.collections.ObservableList;
import model.Case;
import model.Item;

public interface IDatenhaltung {

	ObservableList<Case>  getAllCases();
	void deleteCase(int id);
	void editCase(Case editCase);
	void dbConnection();
	void createCase(Case createCase);
	Case findCase(int id);

	ObservableList<Item> getItemFromCase(int id);
	void createItem(Item item);
	void deleteItem(int id);
	void editItem(Item editItem);
	
	void setItemsToGround(int id);
	int getItemsWeight(int caseID);
}
