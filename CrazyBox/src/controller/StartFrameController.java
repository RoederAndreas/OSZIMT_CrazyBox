package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.Case;
import model.Item;
import service.IDatenhaltung;
import view.Gui;

public class StartFrameController implements Initializable{

	private IFachkonzept fachkonzept;
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
			if (listCase.getSelectionModel().getSelectedItem() != null && !listCase.getSelectionModel().getSelectedItem().getName().equals("Boden")){
				EditCaseFrameController editCon = new EditCaseFrameController(this);
				editCon.showEditFrame("editCaseFrame.fxml");
				selctedCase = listCase.getSelectionModel().getSelectedItem();
			}else{System.out.println("W�hle etwas aus!");}
		});

		btnDeleteCase.setOnAction(event -> {
			if (listCase.getSelectionModel().getSelectedItem() != null && !listCase.getSelectionModel().getSelectedItem().getName().equals("Boden")){
				fachkonzept.deleteCase(listCase.getSelectionModel().getSelectedItem().getId());
				fachkonzept.setItemsToGround(listCase.getSelectionModel().getSelectedItem().getId());
				listCase.getItems().setAll(fachkonzept.showAllCases());
			}else{System.out.println("W�hle etwas aus!");}
		});

		listCase.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (listCase.getSelectionModel().getSelectedItem() != null){
					listItems.setItems(fachkonzept.findItemsFromCase(listCase.getSelectionModel().getSelectedItem().getId()));
				}
			}
		});

		listItems.setOnMouseClicked(event -> {
			if (listItems.getSelectionModel().getSelectedItem() != null){
				if (event.getClickCount() == 2){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Informationen");
					alert.setHeaderText(null);
					alert.setContentText("Bezeichnung: " + listItems.getSelectionModel().getSelectedItem().getDesignation() +
							"\n" + "Masse: " + listItems.getSelectionModel().getSelectedItem().getWeight() +
							"\n" + "Beschreibung: " + listItems.getSelectionModel().getSelectedItem().getDescription() +
							"\n" + "Kiste: " + listItems.getSelectionModel().getSelectedItem().getSelectionCase().getName()
							);
					DialogPane dialogPane = alert.getDialogPane();
					dialogPane.getStylesheets().add(Gui.class.getResource("application.css").toExternalForm());
					dialogPane.getStyleClass().add("myDialog");
					alert.showAndWait();
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
			}else{System.out.println("W�hle etwas aus!");}
		});

		btnDeleteItem.setOnAction(event -> {
			if (listItems.getSelectionModel().getSelectedItem() != null){
				fachkonzept.deleteItem(listItems.getSelectionModel().getSelectedItem().getId());
				listItems.getItems().setAll(fachkonzept.findItemsFromCase(listItems.getSelectionModel().getSelectedItem().getSelectionCase().getId()));
			}else{System.out.println("W�hle etwas aus!");}
		});
	}

	public IFachkonzept getFachkonzept(){
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
