package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import entities.Case;
import entities.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	
//	@Override
//	public Case findCase(int id) {
//		try {
//			Statement stmt = connection.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT * FROM Kiste WHERE kisteID="+id);
//			Case myCase = new Case(rs.getInt(3), rs.getInt(1), rs.getString(2));
//			return myCase;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
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
	public List<Item> getAllItems() {
		return null;
	}

	@Override
	public Item findItem(int id) {
		return null;
	}
}
