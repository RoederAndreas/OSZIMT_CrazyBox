package controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.Gui;

public abstract class StageController {
	
	public void createNewStage(String name){
		try {
			Stage stage = new Stage();
			Parent page = FXMLLoader.load(Gui.class.getResource(name));
			Scene scene = new Scene(page);
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeStage(Button button){
		Stage stage = (Stage) button.getScene().getWindow();
	    stage.close();
	}
	
	public boolean isNumeric(String input){
		try{
			Integer.parseInt(input);
		}catch(Exception e){
			return false;
		}
		return true;
	}
}
