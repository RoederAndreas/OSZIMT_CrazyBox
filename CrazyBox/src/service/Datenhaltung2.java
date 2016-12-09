package service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Case;
import model.Item;

public class Datenhaltung2 implements IDatenhaltung {

	private File dataFile = null;
	private JSONObject dataObject = null;
	private int caseAutoId = 0;
	private int itemAutoId = 0;

	private int getCaseAutoId() {
		return ++caseAutoId;
	}

	private int getItemAutoId() {
		return ++itemAutoId;
	}

	private void setAutoIds() {
		JSONArray cases = dataObject.getJSONArray("cases");
		for (int i = 0; i < cases.length(); i++) {
			int caseId = cases.getJSONObject(i).getInt("id");
			if (caseId > caseAutoId) caseAutoId = caseId;
		}
		JSONArray items = dataObject.getJSONArray("items");
		for (int i = 0; i < items.length(); i++) {
			int itemId = items.getJSONObject(i).getInt("id");
			if (itemId > itemAutoId) itemAutoId = itemId;
		}
		System.out.println("caseAutoId: " + caseAutoId + "; itemAutoId: " + itemAutoId);
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
			FileWriter dataFileWriter = new FileWriter(dataFile);
			dataFileWriter.write(dataObject.toString());
			dataFileWriter.flush();
			dataFileWriter.close();
		} catch (IOException e) {System.err.println("IOException: Writing to file\n" + e.getMessage());}
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
		int index = getCaseIndexById(id);
		dataObject.getJSONArray("cases").remove(index);
		saveChanges();
	}

	@Override
	public void editCase(Case editCase) {
		int index = getCaseIndexById(editCase.getId());
		dataObject.getJSONArray("cases").put(index, JSONObject.wrap(editCase));
		saveChanges();
	}

	@Override
	public void dbConnection() {
		dataFile = new File("data.json");
		dataObject = new JSONObject();
		try {
			if (!dataFile.exists()) { // TODO: should also apply if file exists, but is empty or doesn't contain valid JSON
				dataFile.createNewFile();
				dataObject.put("cases", new JSONArray());
				dataObject.put("items", new JSONArray());
			} else {
				byte[] encoded = Files.readAllBytes(Paths.get("data.json"));
				String decoded = new String(encoded);
				System.out.println(decoded);
				dataObject = new JSONObject(decoded);
				setAutoIds();
			}
			saveChanges();
		} catch (IOException e) {System.err.println("IOException: File creation");}
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
		ObservableList<Item> items = FXCollections.observableArrayList();
		JSONArray itemData = dataObject.getJSONArray("items");
		for (int i = 0; i < itemData.length(); i++) {
			JSONObject item = itemData.getJSONObject(i);
			
			int caseId = item.getInt("selectionCase");
			if (caseId != id) continue;
			
			int itemId = item.getInt("id");
			int weight = item.getInt("weight");
			String designation = item.getString("designation");
			String description = item.getString("description");
			
			Item realItem = new Item(itemId, designation, weight, description, findCase(caseId));
			items.add(realItem);
		}
		return items;
	}

	@Override
	public void createItem(Item item) {
		item.setId(getItemAutoId());
		dataObject.append("items", JSONObject.wrap(item));
		correctItemToCaseRelation(item);
		saveChanges();
	}

	@Override
	public void deleteItem(int id) {
		int index = getItemIndexById(id);
		dataObject.getJSONArray("items").remove(index);
		saveChanges();
	}

	@Override
	public void editItem(Item editItem) {
		int index = getItemIndexById(editItem.getId());
		dataObject.getJSONArray("items").put(index, JSONObject.wrap(editItem));
		correctItemToCaseRelation(editItem);
		saveChanges();
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

	private void correctItemToCaseRelation(Item item) {
		int index = getItemIndexById(item.getId());
		JSONObject jsonItem = dataObject.getJSONArray("items").getJSONObject(index);
		jsonItem.put("selectionCase", jsonItem.getJSONObject("selectionCase").getInt("id"));
		saveChanges();
	}

}
