package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Case;
import model.Item;

public class Datenhaltung1 implements IDatenhaltung{

	private Connection connection = null;
	
	@Override
	public void dbConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/da/Desktop/SchulDB");
			System.out.println("Erfolgreich verbunden!");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Fehler!");
			e.printStackTrace();
		}
	}
	
	@Override
	public void createCase(Case createCase) {
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO Kiste (payload, name) VALUES (?, ?)");
			statement.setInt(1, createCase.getPayload());
			statement.setString(2, createCase.getName());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCase(int id) {
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM Kiste WHERE kisteID="+id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editCase(Case editCase) {
		try {
			PreparedStatement statement = connection.prepareStatement("Update Kiste SET payload=?, name=? WHERE kisteID="+editCase.getId());
			statement.setInt(1, editCase.getPayload());
			statement.setString(2, editCase.getName());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ObservableList<Case> getAllCases() {
		ObservableList<Case> allCases = FXCollections.observableArrayList();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Kiste");
			while (rs.next()) {
				int id = rs.getInt("kisteID");
				int payload = rs.getInt("payload");
				String name = rs.getString("name");
				Case myCase = new Case(id, payload, name);
				allCases.add(myCase);
			}
			return allCases;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ObservableList<Item> getItemFromCase(int id) {
		ObservableList<Item> allItems = FXCollections.observableArrayList();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Gegenstand WHERE fk_kiste_id="+id);
			while (rs.next()) {
				int gID = rs.getInt("gegenstandID");
				String designation = rs.getString("bezeichnung");
				int weight = rs.getInt("masse");
				String description = rs.getString("beschreibung");
				int myCase = rs.getInt("fk_kiste_id");
				Case selectionCase = findCase(myCase);
				Item myItem = new Item(gID, designation, weight, description, selectionCase);
				allItems.add(myItem);
			}
			return allItems;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Case findCase(int id) {
		Case findCase = null;
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Kiste WHERE kisteID="+id);
			while (rs.next()) {
				int kID = rs.getInt("kisteID");
				int payload = rs.getInt("payload");
				String name = rs.getString("name");
				findCase = new Case(kID, payload, name);
			}
			return findCase;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void createItem(Item item) {
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO Gegenstand (bezeichnung, masse, beschreibung, gegenstandID, fk_kiste_id) VALUES (?, ?, ?, ?, ?)");
			statement.setString(1, item.getDesignation());
			statement.setInt(2, item.getWeight());
			statement.setString(3, item.getDescription());
			statement.setInt(5, item.getSelectionCase().getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteItem(int id) {
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM Gegenstand WHERE gegenstandID="+id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editItem(Item editItem) {
		try {
			PreparedStatement statement = connection.prepareStatement("Update Gegenstand SET bezeichnung=?, masse=?, beschreibung=?, gegenstandID=?, fk_kiste_id=? WHERE gegenstandID="+editItem.getId());
			statement.setString(1, editItem.getDesignation());
			statement.setInt(2, editItem.getWeight());
			statement.setString(3, editItem.getDescription());
			statement.setInt(4, editItem.getId());
			statement.setInt(5, editItem.getSelectionCase().getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setItemsToGround(int id) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("Update Gegenstand SET fk_kiste_id=? WHERE fk_kiste_id="+id);
			statement.setInt(1, 19);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Masse der Gegenstände zur bestimmten Kiste müssen zusammen gerechnet werden und ein Int zurückgegeben werden!
	@Override
	public int getItemsWeight(int caseID) {
		int sumAllItems = 0;
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT SUM(masse) FROM Gegenstand WHERE fk_kiste_id="+caseID);
			while (rs.next()) {
				sumAllItems = rs.getInt(1);
			}
			return sumAllItems;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
