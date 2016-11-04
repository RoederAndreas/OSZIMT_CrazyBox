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
	private static StartFrameController startController;

	public CreateCaseFrameController(){}

	public CreateCaseFrameController(StartFrameController startController){
		this.startController = startController;
	}

	@FXML
	public void initialize(){
		createListener();
	}

	public void showCreateFrame(String name){
		createNewStage(name);
	}

	private void createListener(){
		btnSaveCase.setOnAction(event -> {
			if (!txtNameCase.getText().trim().equals("Boden")){
				if (!txtNameCase.getText().trim().equals("") && !txtPayloadCase.getText().trim().equals("") && isNumeric(txtPayloadCase.getText().trim()) == true){
					startController.getFachkonzept().createCase(Integer.parseInt(txtPayloadCase.getText().trim()), txtNameCase.getText().trim());
					startController.getListViewCase().getItems().setAll(startController.getFachkonzept().showAllCases());
					closeStage(btnSaveCase);
				}else{
					System.out.println("Bitte alles ausfüllen!");
				}
			}else{System.out.println("Boden ist schon vorhanden!");}
		});

		btnCancel.setOnAction(event -> {
			closeStage(btnCancel);
		});
	}
}
