package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Case;

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
		comboSelectionCase.getItems().setAll(startController.getFachkonzept().showAllCases());
		btnSaveItem.setOnAction(event -> {
			if (!txtDesignation.getText().trim().equals("") && !txtWeight.getText().trim().equals("") && !txtWeight.getText().trim().equals("0") && isNumeric(txtWeight.getText().trim()) == true && comboSelectionCase.getSelectionModel().getSelectedItem() != null){
				int sum = Integer.parseInt(txtWeight.getText()) + startController.getFachkonzept().getItemsWeight(startController.getListViewCase().getSelectionModel().getSelectedItem().getId());
				if (startController.getListViewCase().getSelectionModel().getSelectedItem().getPayload() >= sum){
					startController.getFachkonzept().editItem(startController.getSelctedItem().getId(), txtDesignation.getText(), Integer.parseInt(txtWeight.getText()), txtAreaDescription.getText(), comboSelectionCase.getSelectionModel().getSelectedItem());
					startController.getListViewItem().setItems(startController.getFachkonzept().findItemsFromCase(comboSelectionCase.getSelectionModel().getSelectedItem().getId()));
					closeStage(btnSaveItem);
				}else{System.out.println("Gegenstand ist zu schwer!");}
			}else{System.out.println("Nicht alle Pflichtfelder ausgefüllt!");}
		});

		btnCancel.setOnAction(event -> {
			closeStage(btnCancel);
		});
	}
}
