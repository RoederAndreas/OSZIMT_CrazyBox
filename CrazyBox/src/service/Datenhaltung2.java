package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import model.Case;
import model.Item;

public class Datenhaltung2 implements IDatenhaltung {

	private File dataFile = null;
	private FileWriter dataFileWriter = null;
	private FileReader dataFileReader = null;
	private JSONObject dataObject = null;
	private int caseAutoId = 0;
	private int itemAutoid = 0;

	private int getCaseAutoId() {
		return ++caseAutoId;
	}

	private int getItemAutoId() {
		return ++itemAutoid;
	}

	private JSONObject getCaseById(int id) {
		JSONArray cases = dataObject.getJSONArray("cases");
		for (int i = 0; i < cases.length(); i++) {
			if (cases.getJSONObject(i).getInt("id") == id) return cases.getJSONObject(i);
		}
		return null;
	}

	private JSONObject getItemById(int id) {
		JSONArray items = dataObject.getJSONArray("items");
		for (int i = 0; i < items.length(); i++) {
			if (items.getJSONObject(i).getInt("id") == id) return items.getJSONObject(i);
		}
		return null;
	}

	private int getCaseIndexById(int id) {
		JSONArray cases = dataObject.getJSONArray("cases");
		for (int i = 0; i < cases.length(); i++) {
			if (cases.getJSONObject(i).getInt("id") == id) return i;
		}
		return -1;
	}

	private int getItemIndexById(int id) {
		JSONArray items = dataObject.getJSONArray("items");
		for (int i = 0; i < items.length(); i++) {
			if (items.getJSONObject(i).getInt("id") == id) return i;
		}
		return -1;
	}

	private void saveChanges() {
		try {
			dataFileWriter.write(dataObject.toString());
			dataFileWriter.flush();
			dataFileWriter.close();
		} catch (IOException e) {System.err.println("IOException: Writing to file");}
	}

	@Override
	public ObservableList<Case> getAllCases() {
		ObservableList<Case> cases = FXCollections.observableArrayList();
		JSONArray caseData = dataObject.getJSONArray("cases");
		for (int i = 0; i < caseData.length(); i++) {
			JSONObject someCase = caseData.getJSONObject(i);
			Case realCase = new Case(someCase.getInt("id"), someCase.getInt("payload"), someCase.getString("name"));
			cases.add(realCase);
		}
		return cases;
	}

	@Override
	public void deleteCase(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editCase(Case editCase) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dbConnection() {
		// TODO Auto-generated method stub
		dataFile = new File("data.json");
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
				dataObject = new JSONObject();
				dataObject.put("cases", new JSONArray());
				dataObject.put("items", new JSONArray());
				dataFileWriter = new FileWriter(dataFile);
				dataFileReader = new FileReader(dataFile);
				saveChanges();
			} catch (IOException e) {System.err.println("IOException: File creation");}
		}
//		else {
//			try {
//				dataFileWriter = new FileWriter(dataFile);
//				dataFileReader = new FileReader(dataFile);
//				BufferedReader testReader = new BufferedReader(dataFileReader);
//				String input = testReader.readLine();
//				System.out.println(input);
//				dataObject = new JSONObject(input);
//				System.out.println("File Contents: " + dataObject.toString());
//			} catch (IOException e) {System.err.println("IOException: FileReader/Writer initialization");}
//		}
	}

	@Override
	public void createCase(Case createCase) {
		createCase.setId(getCaseAutoId());
		dataObject.append("cases", JSONObject.wrap(createCase));
		saveChanges();
	}

	@Override
	public Case findCase(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObservableList<Item> getItemFromCase(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createItem(Item item) {
		item.setId(getItemAutoId());
		dataObject.append("items", JSONObject.wrap(item));
		saveChanges();
	}

	@Override
	public void deleteItem(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editItem(Item editItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setItemsToGround(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getItemsWeight(int caseID) {
		// TODO Auto-generated method stub
		return 0;
	}

}
