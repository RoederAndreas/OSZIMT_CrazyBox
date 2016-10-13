package service;

import java.util.List;

import entities.Case;
import entities.Item;
import javafx.collections.ObservableList;

public interface IDatenhaltung {

	List<Item> getAllItems();
	ObservableList<Case>  getAllCases();
	Item findItem(int id);
	//Case findCase(int id);
	void deleteCase(int id);
	void editCase(Case editCase);
	void dbConnection();
	void createCase(Case createCase);
}
