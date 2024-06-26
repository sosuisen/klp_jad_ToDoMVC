package com.example;

import java.time.LocalDate;

import com.example.model.ToDo;
import com.example.model.ToDoManager;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainController {

	@FXML
	private Button addBtn;

	@FXML
	private DatePicker headerDatePicker;

	@FXML
	private TextField headerTitleField;

	@FXML
	private VBox todoListVBox;

	private ToDoManager model;

	private HBox createToDoHBox(ToDo todo) {
		// Create View Items
		var completedCheckBox = new CheckBox();
		completedCheckBox.setSelected(todo.isCompleted());
		completedCheckBox.getStyleClass().add("todo-completed");

		var titleField = new TextField(todo.getTitle());
		titleField.getStyleClass().add("todo-title");
		HBox.setHgrow(titleField, Priority.ALWAYS);

		var datePicker = new DatePicker(todo.getDate());
		datePicker.getStyleClass().add("todo-date");
		datePicker.setPrefWidth(105);
		HBox.setHgrow(datePicker, Priority.NEVER);

		var deleteBtn = new Button("削除");
		deleteBtn.getStyleClass().add("todo-delete");

		var todoItem = new HBox(completedCheckBox, titleField, datePicker, deleteBtn);
		todoItem.getStyleClass().add("todo-item");

		// Bind Model to View
		completedCheckBox.selectedProperty().bindBidirectional(todo.completedProperty());
		titleField.textProperty().bindBidirectional(todo.titleProperty());
		datePicker.valueProperty().bindBidirectional(todo.dateProperty());
		
		// Event Handler
		deleteBtn.setOnAction(e -> {
			model.remove(todo);
		});

		return todoItem;
	}

	public void initModel(ToDoManager manager) {
		if (this.model != null)
			throw new IllegalStateException("Model can only be initialized once");

		model = manager;

		ObservableList<Node> todoListItems = todoListVBox.getChildren();

		// Event Handler
		addBtn.setOnAction(e -> {
			model.create(headerTitleField.getText(), headerDatePicker.getValue(), false);
			headerTitleField.clear();
		});

		// Observe Model to update View
		model.todosProperty().addListener((ListChangeListener<ToDo>) change -> {
			while (change.next()) {
				if (change.wasAdded()) {
					change.getAddedSubList().forEach(todo -> todoListItems.add(createToDoHBox(todo)));
				}
				if (change.wasRemoved()) {
					todoListItems.remove(change.getFrom(), change.getTo() + 1);
				}
			}
		});

		model.loadInitialData();
	}

	public void initialize() {
		// Set today
		headerDatePicker.setValue(LocalDate.now());
	}
}