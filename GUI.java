import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUI extends Application {
	private TableView<ToDoItem> tblItems = new TableView<>();
	private static final String STYLE = "-fx-background-color: rgb(40, 40, 43);" + "-fx-font-size: 14";
	private static final String FSTYLE = "-fx-background-color: rgb(40, 40, 43);" + "-fx-text-fill: #D3D3D3;"
			+ "-fx-font-size: 15";
	private static final String BSTYLE = "-fx-background-color: rgb(54, 69, 79);" + "-fx-border-color: rgb(54, 69, 79);"
			+ "-fx-text-fill: #D3D3D3;";
	private static final String TFSTYLE = "-fx-background-color: rgb(54, 69, 79);"
			+ "-fx-border-color: rgb(54, 69, 79);" + "-fx-text-fill: #E5E4E2;";
	private static final String ERRORMSG = "Error Message";
	private final ObservableList<ToDoItem> data = testMethod();

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws Exception {
		tblItems.setEditable(true);
		tblItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		VBox.setVgrow(tblItems, Priority.ALWAYS);
		TableColumn<ToDoItem, String> colContent = new TableColumn<>("Item Content");
		TableColumn<ToDoItem, Boolean> colResolved = new TableColumn<>("Resolved Status");

		colContent.setCellValueFactory(new PropertyValueFactory<>("content"));
		colResolved.setCellValueFactory(new PropertyValueFactory<>("resolved"));
		colContent.setSortable(true);
		colResolved.setSortable(true);
		colResolved.setSortType(TableColumn.SortType.ASCENDING);

		tblItems.getItems().addAll(data);
		tblItems.getColumns().addAll(colContent, colResolved);
		tblItems.getStylesheets().add(GUI.class.getResource("myTable.css").toExternalForm());
		tblItems.getSortOrder().add(colResolved);
		tblItems.sort();

		final TextField addContent = new TextField();
		addContent.setPromptText("Item");
		addContent.setMaxWidth(colContent.getPrefWidth());
		addContent.setStyle(TFSTYLE);
		final TextField renameContent = new TextField();
		renameContent.setPromptText("Name");
		renameContent.setMaxWidth(colContent.getPrefWidth());
		renameContent.setStyle(TFSTYLE);
		Button create = new Button("Add");
		Button delete = new Button("Delete");
		Button resolve = new Button("Resolve");
		Button unresolve = new Button("Unresolve");
		Button rename = new Button("Rename");
		create.setOnAction(value -> {
			if (addContent.getText().isEmpty()) {
				addAlert();
			} else {
				tblItems.getItems().add(new ToDoItem(addContent.getText()));
				addContent.clear();
				tblItems.sort();
			}
		});
		delete.setOnAction(value -> {
			ToDoItem selected = tblItems.getSelectionModel().getSelectedItem();
			if (selected == null) {
				nullAlert();
			} else {
				tblItems.getItems().remove(selected);
				tblItems.sort();
			}
		});
		resolve.setOnAction(value -> {
			ToDoItem selected = tblItems.getSelectionModel().getSelectedItem();
			if (selected == null) {
				nullAlert();
			} else {
				selected.trueResolved();
				tblItems.getItems().set(tblItems.getSelectionModel().getSelectedIndex(), selected);
				tblItems.sort();
			}
		});
		unresolve.setOnAction(value -> {
			ToDoItem selected = tblItems.getSelectionModel().getSelectedItem();
			if (selected == null) {
				nullAlert();
			} else {
				selected.falseResolved();
				tblItems.getItems().set(tblItems.getSelectionModel().getSelectedIndex(), selected);
				tblItems.sort();
			}
		});
		rename.setOnAction(value -> {
			ToDoItem selected = tblItems.getSelectionModel().getSelectedItem();
			if (selected == null) {
				nullAlert();
			} else {
				if (renameContent.getText().isEmpty()) {
					renameAlert();
				} else {
					selected.setContent(renameContent.getText());
					tblItems.getItems().set(tblItems.getSelectionModel().getSelectedIndex(), selected);
					renameContent.clear();
					tblItems.sort();
				}
			}
		});
		create.setStyle(BSTYLE);
		delete.setStyle(BSTYLE);
		rename.setStyle(BSTYLE);
		resolve.setStyle(BSTYLE);
		unresolve.setStyle(BSTYLE);
		Label label = new Label("Select a row for an action:");
		label.setStyle(FSTYLE);
		Label addLabel = new Label("Add new element:");
		addLabel.setStyle(FSTYLE);
		HBox addBox = new HBox(addLabel, addContent, create);
		addBox.setSpacing(8);
		HBox buttonBox = new HBox(label, renameContent, rename, delete, resolve, unresolve);
		buttonBox.setSpacing(8);
		VBox vbox = new VBox(tblItems, addBox, buttonBox);
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(10);
		vbox.setStyle(STYLE);

		Scene scene = new Scene(vbox);

		stage.setTitle("Simple GUI");
		stage.setScene(scene);
		stage.show();
	}

	private ObservableList<ToDoItem> testMethod() {
		ToDoList temp = new ToDoList();
		temp.add(new ToDoItem("Curiosity"));
		temp.add(new ToDoItem("Unlimited POWER!"));
		temp.add(new ToDoItem("UwU"));
		temp.add(new ToDoItem("Shrek is love"));
		temp.add(new ToDoItem("Change my mind"));
		temp.add(new ToDoItem("I hate penguins"));
		return temp.getItems();
	}

	private void nullAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(ERRORMSG);
		alert.setHeaderText(null);
		alert.setContentText("Select something first!");
		alert.showAndWait();
	}

	private void renameAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(ERRORMSG);
		alert.setHeaderText(null);
		alert.setContentText("New name should not be empty!");
		alert.showAndWait();
	}

	private void addAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(ERRORMSG);
		alert.setHeaderText(null);
		alert.setContentText("You can not create an empty item!");
		alert.showAndWait();
	}

}
