package com.example.model;

import java.time.LocalDate;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
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

	public void create(String title, LocalDate date, boolean completed) {
		int newId;
		if (todos.size() > 0)
			newId = todos.stream().max((todo1, todo2) -> todo1.getId() - todo2.getId()).get().getId() + 1;
		else
			newId = 0;

		var newToDo = new ToDo(newId, title, date, false);
		newToDo.titleProperty().addListener((observable, oldValue, newValue) -> updateTitle(newToDo.getId(), newValue));		
		newToDo.dateProperty().addListener((observable, oldValue, newValue) -> updateDate(newToDo.getId(), newValue));
		newToDo.completedProperty().addListener((observable, oldValue, newValue) -> updateCompleted(newToDo.getId(), newValue));

		todos.add(newToDo);

		System.out.println("Added #" + newId);
	}
	
	private void load(int id, String title, LocalDate date, boolean completed) {
		var todo = new ToDo(id, title, date, completed);
		todo.titleProperty().addListener((observable, oldValue, newValue) -> updateTitle(todo.getId(), newValue));		
		todo.dateProperty().addListener((observable, oldValue, newValue) -> updateDate(todo.getId(), newValue));
		todo.completedProperty().addListener((observable, oldValue, newValue) -> updateCompleted(todo.getId(), newValue));
		
		todos.add(todo);
	}
	
	public void loadInitialData() {
		load(0, "Design", LocalDate.parse("2022-12-01"), true);
		load(1, "Implementation", LocalDate.parse("2022-12-07"), false);
	}

	private void updateTitle(int id, String title) {
		System.out.println("Title changed #" + id + " : " + title);
	}

	private void updateDate(int id, LocalDate date) {
		System.out.println("Date changed #" + id + " : " + date);
	}
	
	private void updateCompleted(int id, boolean completed) {
		System.out.println("Completed changed #" + id + " : " + completed);		
	}
}
