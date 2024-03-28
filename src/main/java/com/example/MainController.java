package com.example;

import java.time.LocalDate;

import com.example.model.ToDo;
import com.example.model.ToDoManager;

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
    
	private ObservableList<Node> todoListItems;


	private HBox createToDoHBox(ToDo todo) {
		// Create View Item
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
		completedCheckBox.selectedProperty().bindBidirectional(todo.getCompletedProperty());
		titleField.textProperty().bindBidirectional(todo.getTitleProperty());
		datePicker.valueProperty().bindBidirectional(todo.getDateProperty());

		// Event Handler
		deleteBtn.setOnAction(e -> {
			model.remove(todo);
			todoListItems.remove(todoItem);
		});

		return todoItem;
	}
	
    public void setModel(ToDoManager manager) {
		if (this.model != null)
			throw new IllegalStateException("Model can only be initialized once");
		
    	model = manager;

		for (var todo : model.getToDos()) {
			todoListItems.add(createToDoHBox(todo));
		}
    	
		// Event Handler
    	addBtn.setOnAction(e -> {
    		var todo = model.create(headerTitleField.getText(), headerDatePicker.getValue());
    		todoListItems.add(createToDoHBox(todo));
    		headerTitleField.clear();
    	});
    }
    
	public void initialize() {
		// Set today
		headerDatePicker.setValue(LocalDate.now());
		
		todoListItems = todoListVBox.getChildren();
	}
}