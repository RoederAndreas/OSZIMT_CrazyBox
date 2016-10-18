package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EditCaseFrameController extends StageController implements Initializable{

	@FXML
	private TextField txtCaseName;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnCancel;
	private static StartFrameController startController;
	
	public EditCaseFrameController(){}

	public EditCaseFrameController(StartFrameController startController){
		this.startController = startController;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		createListener();
	}
	
	public void showEditFrame(String name){
		createNewStage(name);
	}
	
	private void createListener(){
		btnEdit.setOnAction(event ->{
			if (!txtCaseName.getText().trim().equals("") && !txtCaseName.getText().trim().equals("Boden")){
				startController.getFachkonzept().editCase(startController.getSelctedCase().getId(), startController.getSelctedCase().getPayload(), txtCaseName.getText());
				startController.getListViewCase().getItems().setAll(startController.getFachkonzept().showAllCases());
				closeStage(btnEdit);
			}
		});
		
		btnCancel.setOnAction(event -> {
			closeStage(btnCancel);
		});
	}
}
