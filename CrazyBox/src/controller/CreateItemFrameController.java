package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Case;

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
	private ObservableList<Case> allCases = FXCollections.observableArrayList();

	public CreateItemFrameController(){}

	public CreateItemFrameController(StartFrameController startController){
		this.startController = startController;
	}

	@FXML
	public void initialize(){
		for (Case case1 : startController.getAllCases()) {
			if(!case1.getName().equals("Boden")){
				allCases.add(case1);
			}
		}

		comboSelectionCase.setItems(allCases);
		createListener();
	}

	public void showCreateFrame(String name){
		createNewStage(name);
	}

	private void createListener(){
		btnCreateItem.setOnAction(event -> {
			if (!txtDesignation.getText().trim().equals("") && !txtWeight.getText().trim().equals("") && !txtWeight.getText().trim().equals("0") && isNumeric(txtWeight.getText().trim()) == true && comboSelectionCase.getSelectionModel().getSelectedItem() != null){
				int sum = Integer.parseInt(txtWeight.getText()) + startController.getFachkonzept().getItemsWeight(startController.getListViewCase().getSelectionModel().getSelectedItem().getId());
				if (startController.getListViewCase().getSelectionModel().getSelectedItem().getPayload() >= sum){
				startController.getFachkonzept().createItem(txtDesignation.getText(), Integer.parseInt(txtWeight.getText()), txtAreaDescription.getText(), comboSelectionCase.getSelectionModel().getSelectedItem());
				startController.getListViewItem().setItems(startController.getFachkonzept().findItemsFromCase(comboSelectionCase.getSelectionModel().getSelectedItem().getId()));
				closeStage(btnCreateItem);
				}else{System.out.println("Gegenstand zu schwer!");}
			}else{System.out.println("Nicht alle Pflichtfelder ausgefüllt!");}
		});

		btnCancel.setOnAction(event -> {
			closeStage(btnCancel);
		});
	}
}
