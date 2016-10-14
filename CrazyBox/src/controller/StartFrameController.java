package controller;

import java.net.URL;
import java.util.ResourceBundle;
import entities.Case;
import entities.Item;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import service.IDatenhaltung;

public class StartFrameController implements Initializable{

	private static IFachkonzept fachkonzept;
	private IDatenhaltung datenhaltung;
	
	@FXML
	private ListView<Case> listCase;
	@FXML
	private ListView<Item> listItems;
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
	private ObservableList<Case> getAllCases ;
	
	public StartFrameController(IFachkonzept fachkonzept, IDatenhaltung datenhaltung) {
		this.fachkonzept = fachkonzept;
		this.datenhaltung = datenhaltung;
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
	
	private Callback<ListView<Item>,ListCell<Item>> listCallbackItemList = new Callback<ListView<Item>,ListCell<Item>>() {

		@Override
		public ListCell<Item> call(ListView<Item> param) {
			ListCell<Item> t = new ListCell<Item>(){
				protected void updateItem(Item item, boolean empty) {
					super.updateItem(item,empty);
					if(item != null){
						setText(item.getDesignation());
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
		listCase.setCellFactory(listCallbackCaseList);
		getAllCases = fachkonzept.showAllCases();
		listCase.setItems(fachkonzept.showAllCases());
		addListener();
	}
	
	private void addListener(){
		btnCreateCase.setOnAction(event -> {
			CreateCaseFrameController createCon = new CreateCaseFrameController(this);
			createCon.showCreateFrame("createCaseFrame.fxml");
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
				listCase.getItems().setAll(fachkonzept.showAllCases());
			}else{System.out.println("Wähle etwas aus!");}
		});	
		
		listCase.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				listItems.setCellFactory(listCallbackItemList);
				listItems.setItems(fachkonzept.findItemsFromCase(listCase.getSelectionModel().getSelectedItem().getId()));
			}
		});
		
		btnCreateItem.setOnAction(event -> {
			//Noch nicht korrekt 
			CreateItemFrameController createItemCon = new CreateItemFrameController(this);
			createItemCon.showCreateFrame("createItemFrame.fxml");
		});
	}
	
	public static IFachkonzept getFachkonzept(){
		return fachkonzept;
	}
	
	public Case getSelctedCase(){
		return selctedCase;
	}
	
	public ListView<Case> getListViewCase(){
		return listCase;
	}
	
	public ObservableList<Case> getAllCases(){
		return getAllCases;
	}
}
