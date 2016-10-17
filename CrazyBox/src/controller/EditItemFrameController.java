package controller;

import entities.Case;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditItemFrameController extends StageController{

	@FXML
	private TextField txtDesignation;
	@FXML
	private TextField txtWeight;
	@FXML
	private TextArea txtAreaDescription;
	@FXML
	private ComboBox<Case> comboSelectionCase;
	@FXML
	private Button btnSaveItem;
	@FXML
	private Button btnCancel;
	private static StartFrameController startController;
	
	public EditItemFrameController(){}
	
	public EditItemFrameController(StartFrameController startController){
		this.startController = startController;
	}
	
	@FXML
	public void initialize(){
		comboSelectionCase.setItems(startController.getAllCases());
		setAllValues();
		createListener();
	}
	
	public void showEditFrame(String name){
		createNewStage(name);
	}
	
	private void setAllValues(){
		txtDesignation.setText(startController.getSelctedItem().getDesignation());
		txtWeight.setText(Integer.toString(startController.getSelctedItem().getWeight()));
		txtAreaDescription.setText(startController.getSelctedItem().getDescription());
		comboSelectionCase.setValue(startController.getSelctedItem().getSelectionCase());
	}
	
	private void createListener(){
		
		btnSaveItem.setOnAction(event -> {
			startController.getFachkonzept().editItem(startController.getSelctedItem().getId(), txtDesignation.getText(), Integer.parseInt(txtWeight.getText()), txtAreaDescription.getText(), comboSelectionCase.getSelectionModel().getSelectedItem());
			startController.getListViewItem().setItems(startController.getFachkonzept().findItemsFromCase(comboSelectionCase.getSelectionModel().getSelectedItem().getId()));
			closeStage(btnSaveItem);
		});
		
		btnCancel.setOnAction(event -> {
			closeStage(btnCancel);
		});
	}
}
