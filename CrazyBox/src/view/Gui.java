package view;

import controller.IFachkonzept;
import controller.StartFrameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.IDatenhaltung;

public class Gui extends Application {

	private static IFachkonzept fachkonzept = null;
	private static IDatenhaltung datenhaltung = null;

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("startFrame.fxml"));
			loader.setController(new StartFrameController(getIFachkonzept(),getIDatenhaltung()));
	        Parent root = loader.load();
	        primaryStage.setScene(new Scene(root));
	        primaryStage.setMinWidth(700);
	        primaryStage.setMinHeight(500);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setIFachkonzept(IFachkonzept fachkonzept){
		this.fachkonzept = fachkonzept;
	}

	public IFachkonzept getIFachkonzept(){
		return fachkonzept;
	}

	public void setIDatenhaltung(IDatenhaltung datenhaltung){
		this.datenhaltung = datenhaltung;
	}

	public IDatenhaltung getIDatenhaltung(){
		return datenhaltung;
	}

	public void startFXMLGUI(String[] args){
		launch(args);
	}
}
