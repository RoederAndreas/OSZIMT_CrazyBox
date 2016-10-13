package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateCaseFrameController extends StageController{

	@FXML
	private TextField txtNameCase;
	@FXML
	private TextField txtPayloadCase;
	@FXML
	private Button btnSaveCase;
	@FXML
	private Button btnCancel;
	private StartFrameController startController;
	
	public CreateCaseFrameController(){}
	
	@FXML
	public void initialize(){
		createListener();
	}
	
	public void showCreateFrame(String name){
		createNewStage(name);
	}
	
	private void createListener(){
		btnSaveCase.setOnAction(event -> {
			if (!txtNameCase.getText().trim().equals("") && !txtPayloadCase.getText().trim().equals("")){
				startController.getFachkonzept().createCase(Integer.parseInt(txtPayloadCase.getText().trim()), txtNameCase.getText().trim());
				closeStage(btnSaveCase);
			}else{
				System.out.println("Bitte alles ausfüllen!");
			}
		});
		
		btnCancel.setOnAction(event -> {
			closeStage(btnCancel);
		});
	}
}
