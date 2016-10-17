package controller;

import entities.Case;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateItemFrameController extends StageController{

	@FXML
	private TextField txtDesignation;
	@FXML
	private TextField txtWeight;
	@FXML
	private TextArea txtAreaDescription;
	@FXML
	private ComboBox<Case> comboSelectionCase;
	@FXML
	private Button btnCreateItem;
	@FXML
	private Button btnCancel;
	private static StartFrameController startController;
	
	public CreateItemFrameController(){}
	
	public CreateItemFrameController(StartFrameController startController){
		this.startController = startController;
	}
	
	@FXML
	public void initialize(){
		comboSelectionCase.setItems(startController.getAllCases());
		createListener();
	}
	
	public void showCreateFrame(String name){
		createNewStage(name);
	}
	
	private void createListener(){
		btnCreateItem.setOnAction(event -> {
			if (!txtDesignation.getText().trim().equals("") && !txtWeight.getText().trim().equals("") && isNumeric(txtWeight.getText().trim()) == true && comboSelectionCase.getSelectionModel().getSelectedItem() != null){
				startController.getFachkonzept().createItem(txtDesignation.getText(), Integer.parseInt(txtWeight.getText()), txtAreaDescription.getText(), comboSelectionCase.getSelectionModel().getSelectedItem());
				startController.getListViewItem().setItems(startController.getFachkonzept().findItemsFromCase(comboSelectionCase.getSelectionModel().getSelectedItem().getId()));
				closeStage(btnCreateItem);
			}else{System.out.println("Nicht alle Pflichtfelder ausgefüllt!");}
		});
		
		btnCancel.setOnAction(event -> {
			closeStage(btnCancel);
		});
	}
}
