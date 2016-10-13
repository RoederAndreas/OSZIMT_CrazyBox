package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EditCaseFrameController extends StageController{

	@FXML
	private TextField txtCaseName;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnCancel;
	private StartFrameController startController;
	
	public EditCaseFrameController(){}
	
	@FXML
	public void initialize(){
		createListener();
	}
	
	public void showEditFrame(String name){
		createNewStage(name);
	}
	
	private void createListener(){
		btnEdit.setOnAction(event ->{
			if (!txtCaseName.getText().trim().equals("")){
				//startController.getFachkonzept().editCase(id, payload, name);
				closeStage(btnEdit);
			}
		});
		
		btnCancel.setOnAction(event -> {
			closeStage(btnCancel);
		});
	}
}
