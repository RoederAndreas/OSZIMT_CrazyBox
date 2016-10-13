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
	
	public StartFrameController(IFachkonzept fachkonzept, IDatenhaltung datenhaltung) {
		this.fachkonzept = fachkonzept;
		this.datenhaltung = datenhaltung;
	}
	
	private Callback<ListView<Case>,ListCell<Case>> listCallback = new Callback<ListView<Case>,ListCell<Case>>() {

		@Override
		public ListCell<Case> call(ListView<Case> param) {
			ListCell<Case> t = new ListCell<Case>(){
				protected void updateItem(Case item, boolean empty) {
					if (empty){}
					if(item != null){
						setText(item.getName());
					}
				};
			};
			return t;
		}
	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fachkonzept.setDatenhaltung(datenhaltung);
		listCase.setCellFactory(listCallback);
		listCase.setItems(fachkonzept.showAllCases());
		
		btnCreateCase.setOnAction((event) -> {
			CreateCaseFrameController createCon = new CreateCaseFrameController();
			createCon.showCreateFrame("createCaseFrame.fxml");
		});
		
		btnRenameCase.setOnAction((event) -> {
			//fachkonzept.editCase(1, 200, "Marcus Kiste");
		});
		
		btnDeleteCase.setOnAction((event) -> {
			//fachkonzept.deleteCase();
		});
	}
	
	public static IFachkonzept getFachkonzept(){
		return fachkonzept;
	}
}
