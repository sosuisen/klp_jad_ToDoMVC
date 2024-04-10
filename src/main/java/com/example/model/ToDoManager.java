package com.example.model;

import java.time.LocalDate;

import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;

public class ToDoManager {
	private ListProperty<ToDo> todos = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	public ListProperty<ToDo> todosProperty() {
		return todos;
	}
	
	public void remove(ToDo todo) {
		todos.remove(todo);
		System.out.println("Removed #" + todo.getId());
	}

    private ChangeListener<Object> getListener(int id) {
    	return (observable, oldValue, newValue) -> {
    		switch (((Property<?>)observable).getName()) {
				case "title" -> System.out.println("Title changed #" + id + " : " + newValue);
				case "date" -> System.out.println("Date changed #" + id + " : " + newValue);
				case "completed" -> System.out.println("Completed changed #" + id + " : " + newValue);
    		}
    	};
    }
     
	public void create(String title, LocalDate date, boolean completed) {
		int newId;
		if (todos.size() > 0)
			newId = todos.stream().max((todo1, todo2) -> todo1.getId() - todo2.getId()).get().getId() + 1;
		else
			newId = 0;
		
		var newToDo = new ToDo(newId, title, date, false);
		newToDo.titleProperty().addListener(getListener(newId));		
		newToDo.dateProperty().addListener(getListener(newId));		
		newToDo.completedProperty().addListener(getListener(newId));		

		todos.add(newToDo);

		System.out.println("Added #" + newId);
	}
	
	private void load(int id, String title, LocalDate date, boolean completed) {
		var todo = new ToDo(id, title, date, completed);
		todo.titleProperty().addListener(getListener(id));		
		todo.dateProperty().addListener(getListener(id));		
		todo.completedProperty().addListener(getListener(id));		
		
		todos.add(todo);
	}
	
	public void loadInitialData() {
		load(0, "Design", LocalDate.parse("2022-12-01"), true);
		load(1, "Implementation", LocalDate.parse("2022-12-07"), false);
	}
}
