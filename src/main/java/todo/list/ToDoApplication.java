package todo.list;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import todo.list.logic.ToDoItem;

public class ToDoApplication extends Application {
	private TableView<ToDoItem> tableView = new TableView<>();
	private static final String VBOX_STYLE = "-fx-background-color: rgb(40, 40, 43);"
			+ "-fx-font-size: 14";
	private static final String LABEL_STYLE = "-fx-background-color: rgb(40, 40, 43);"
			+ "-fx-text-fill: #D3D3D3;"
			+ "-fx-font-size: 15";
	private static final String BUTTON_STYLE = "-fx-background-color: rgb(54, 69, 79);"
			+ "-fx-border-color: rgb(54, 69, 79);"
			+ "-fx-text-fill: #D3D3D3;";
	private static final String TEXT_FIELD_STYLE = "-fx-background-color: rgb(54, 69, 79);"
			+ "-fx-border-color: rgb(54, 69, 79);"
			+ "-fx-text-fill: #E5E4E2;";
	private static final String ERROR_MESSAGE = "Error Message";
	private static final String NULL_ERROR_MESSAGE = "Select something first!";
	private static final double HBOX_SPACING = 8;
	private static final double VBOX_SPACING = 10;

	// Used to launch the application.
	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws Exception {
		VBox.setVgrow(tableView, Priority.ALWAYS);

		// Table columns setup.
		TableColumn<ToDoItem, String> colContent = new TableColumn<>("Item");
		TableColumn<ToDoItem, Boolean> colResolved = new TableColumn<>("Resolved");
		colContent.setCellValueFactory(new PropertyValueFactory<>("content"));
		colResolved.setCellValueFactory(new PropertyValueFactory<>("resolved"));
		colContent.setSortable(true);
		colResolved.setSortable(true);
		colResolved.setSortType(TableColumn.SortType.ASCENDING);

		// Table setup.
		tableView.setEditable(true);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.getColumns().addAll(colContent, colResolved);
		tableView.getStylesheets().add(getClass().getClassLoader().getResource("TableView.css").toExternalForm());
		tableView.getSortOrder().add(colResolved);
		tableView.sort();

		// Main elements setup.
		TextField addContent = textFieldConfiguration("Item", colContent.getPrefWidth());
		TextField renameContent = textFieldConfiguration("Name", colContent.getPrefWidth());
		Button createItem = buttonConfiguration("Add");
		Button deleteItem = buttonConfiguration("Delete");
		Button resolveItem = buttonConfiguration("Resolve");
		Button unresolveItem = buttonConfiguration("Unresolve");
		Button renameItem = buttonConfiguration("Rename");
		Label informationLabel = new Label("Select a row for an action:");
		informationLabel.setStyle(LABEL_STYLE);
		Label addLabel = new Label("Add a new element:");
		addLabel.setStyle(LABEL_STYLE);

		// Buttons actions setup.
		resolveItem.setOnAction(value -> resolve(true));
		unresolveItem.setOnAction(value -> resolve(false));
		createItem.setOnAction(value -> {
			if (addContent.getText().isEmpty()) {
				customErrorAlert("You can not create an empty item!");
			} else {
				tableView.getItems().add(new ToDoItem(addContent.getText()));
				addContent.clear();
				tableView.sort();
			}
		});
		deleteItem.setOnAction(value -> {
			ToDoItem selected = tableView.getSelectionModel().getSelectedItem();
			if (selected == null) {
				customErrorAlert(NULL_ERROR_MESSAGE);
			} else {
				tableView.getItems().remove(selected);
				tableView.sort();
			}
		});
		renameItem.setOnAction(value -> {
			ToDoItem selected = tableView.getSelectionModel().getSelectedItem();
			if (selected == null) {
				customErrorAlert(NULL_ERROR_MESSAGE);
			} else {
				if (renameContent.getText().isEmpty()) {
					customErrorAlert("New name should not be empty!");
				} else {
					selected.setContent(renameContent.getText());
					tableView.getItems().set(tableView.getSelectionModel().getSelectedIndex(), selected);
					renameContent.clear();
					tableView.sort();
				}
			}
		});

		// Containers setup.
		HBox firstControlsRowBox = new HBox(addLabel, addContent, createItem,
				deleteItem, resolveItem);
		firstControlsRowBox.setAlignment(Pos.CENTER);
		firstControlsRowBox.setSpacing(HBOX_SPACING);
		HBox secondControlsRowBox = new HBox(informationLabel, renameContent, renameItem, unresolveItem);
		secondControlsRowBox.setAlignment(Pos.CENTER);
		secondControlsRowBox.setSpacing(HBOX_SPACING);
		VBox mainBox = new VBox(tableView, firstControlsRowBox, secondControlsRowBox);
		mainBox.setSpacing(VBOX_SPACING);
		mainBox.setStyle(VBOX_STYLE);

		stage.setTitle("Simple GUI");
		stage.setScene(new Scene(mainBox));
		stage.show();
	}

	/**
	 * Changes the resolved status of the item.
	 * 
	 * @param value Actual status to apply.
	 */
	private void resolve(boolean value) {
		ToDoItem selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null) {
			customErrorAlert(NULL_ERROR_MESSAGE);
		} else {
			selected.setResolved(value);
			tableView.getItems().set(tableView.getSelectionModel().getSelectedIndex(), selected);
			tableView.sort();
		}
	}

	/**
	 * Simplifies multiple buttons configuration.
	 * 
	 * @param text Button's text.
	 * @return Configured button.
	 */
	private Button buttonConfiguration(String text) {
		Button temp = new Button(text);
		temp.setStyle(BUTTON_STYLE);
		return temp;
	}

	/**
	 * Simplifies multiple text fields configuration.
	 * 
	 * @param promptText Prompt text of the field.
	 * @param maxWidth   Maximal width according to the table view.
	 * @return Configured text field.
	 */
	private TextField textFieldConfiguration(String promptText, double maxWidth) {
		TextField temp = new TextField();
		temp.setPromptText(promptText);
		temp.setMaxWidth(maxWidth);
		temp.setStyle(TEXT_FIELD_STYLE);
		return temp;
	}

	/**
	 * Show user an error alert with custom content and removed header.
	 * 
	 * @param content Content to display.
	 */
	private void customErrorAlert(String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(ERROR_MESSAGE);
		alert.setHeaderText("");
		alert.setContentText(content);
		alert.showAndWait();
	}
}
