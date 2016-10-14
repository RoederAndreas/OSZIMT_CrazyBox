package controller;

import java.net.URL;
import java.util.ResourceBundle;
import entities.Case;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import service.IDatenhaltung;

public class StartFrameController implements Initializable{

	private static IFachkonzept fachkonzept;
	private IDatenhaltung datenhaltung;
	
	@FXML
	private ListView<Case> listCase;
	@FXML
	private ListView<String> listItems;
	@FXML
	private Button btnCreateCase;
	@FXML
	private Button btnRenameCase;
	@FXML
	private Button btnDeleteCase;
	@FXML
	private Button btnCreateItem;
	@FXML
	private Button btnEditItem;
	@FXML
	private Button btnDeleteItem;
	private Case selctedCase;
	
	public StartFrameController(IFachkonzept fachkonzept, IDatenhaltung datenhaltung) {
		this.fachkonzept = fachkonzept;
		this.datenhaltung = datenhaltung;
	}
	
	private Callback<ListView<Case>,ListCell<Case>> listCallback = new Callback<ListView<Case>,ListCell<Case>>() {

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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fachkonzept.setDatenhaltung(datenhaltung);
		fachkonzept.createDBConnection();
		listCase.setCellFactory(listCallback);
		listCase.setItems(fachkonzept.showAllCases());
		addListener();
	}
	
	private void addListener(){
		btnCreateCase.setOnAction(event -> {
			CreateCaseFrameController createCon = new CreateCaseFrameController();
			createCon.showCreateFrame("createCaseFrame.fxml");
			listCase.refresh();
		});
		
		btnRenameCase.setOnAction(event -> {
			if (listCase.getSelectionModel().getSelectedItem() != null){
				EditCaseFrameController editCon = new EditCaseFrameController(this);
				editCon.showEditFrame("editCaseFrame.fxml");
				selctedCase = listCase.getSelectionModel().getSelectedItem();
			}else{System.out.println("Wähle etwas aus!");}
		});
		
		btnDeleteCase.setOnAction(event -> {
			if (listCase.getSelectionModel().getSelectedItem() != null){
				fachkonzept.deleteCase(listCase.getSelectionModel().getSelectedItem().getId());
			}else{System.out.println("Wähle etwas aus!");}
		});	
	}
	
	public static IFachkonzept getFachkonzept(){
		return fachkonzept;
	}
	
	public Case getSelctedCase(){
		return selctedCase;
	}
}
