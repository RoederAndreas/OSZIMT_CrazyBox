package controller;

import entities.Case;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;

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
		comboSelectionCase.setCellFactory(listCallbackCaseList);
		comboSelectionCase.setItems(startController.getAllCases());
		createListener();
	}
	
	public void showCreateFrame(String name){
		createNewStage(name);
	}
	
	private Callback<ListView<Case>,ListCell<Case>> listCallbackCaseList = new Callback<ListView<Case>,ListCell<Case>>() {

		@Override
		public ListCell<Case> call(ListView<Case> param) {
			ListCell<Case> t = new ListCell<Case>(){
				protected void updateItem(Case item, boolean empty) {
					super.updateItem(item,empty);
					if(item != null){
						setText(item.getName());
					}else{
						setText(null);
					}
				};
			};
			return t;
		}
	};
	
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
	
	private boolean isNumeric(String input){
		try{
			Integer.parseInt(input);
		}catch(Exception e){
			return false;
		}
		return true;
	}
}
