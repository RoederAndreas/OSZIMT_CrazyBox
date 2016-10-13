package controller;

import entities.Case;
import javafx.collections.ObservableList;
import service.IDatenhaltung;

public interface IFachkonzept {

	void setDatenhaltung(IDatenhaltung datenhaltung);
	void createCase(int payload, String name);
	void editCase(int id, int payload, String name);
	void deleteCase(int id);
	ObservableList<Case> showAllCases();
}
