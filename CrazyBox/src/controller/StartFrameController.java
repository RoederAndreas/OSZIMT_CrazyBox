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
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
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
	private Item selctedItem;
	private ObservableList<Case> getAllCases;
	
	public StartFrameController(IFachkonzept fachkonzept, IDatenhaltung datenhaltung) {
		this.fachkonzept = fachkonzept;
		this.datenhaltung = datenhaltung;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fachkonzept.setDatenhaltung(datenhaltung);
		fachkonzept.createDBConnection();
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
				if (listCase.getSelectionModel().getSelectedItem() != null){
					listItems.setItems(fachkonzept.findItemsFromCase(listCase.getSelectionModel().getSelectedItem().getId()));
				}
			}
		});
		
		btnCreateItem.setOnAction(event -> {
			CreateItemFrameController createItemCon = new CreateItemFrameController(this);
			createItemCon.showCreateFrame("createItemFrame.fxml");
		});
		
		btnEditItem.setOnAction(event -> {
			if (listItems.getSelectionModel().getSelectedItem() != null){
				EditItemFrameController editCon = new EditItemFrameController(this);
				selctedItem = listItems.getSelectionModel().getSelectedItem();
				editCon.showEditFrame("editItemFrame.fxml");
			}else{System.out.println("Wähle etwas aus!");}
		});
		
		btnDeleteItem.setOnAction(event -> {
			if (listItems.getSelectionModel().getSelectedItem() != null){
				fachkonzept.deleteItem(listItems.getSelectionModel().getSelectedItem().getId());
				listItems.getItems().setAll(fachkonzept.findItemsFromCase(listItems.getSelectionModel().getSelectedItem().getSelectionCase().getId()));
			}else{System.out.println("Wähle etwas aus!");}
		});
	}
	
	public static IFachkonzept getFachkonzept(){
		return fachkonzept;
	}
	
	public Case getSelctedCase(){
		return selctedCase;
	}
	
	public Item getSelctedItem(){
		return selctedItem;
	}
	
	public ListView<Case> getListViewCase(){
		return listCase;
	}
	
	public ListView<Item> getListViewItem(){
		return listItems;
	}
	
	public ObservableList<Case> getAllCases(){
		return getAllCases;
	}
}
