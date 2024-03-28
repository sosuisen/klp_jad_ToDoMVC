package com.example.model;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ToDo {
	private StringProperty titleProperty = new SimpleStringProperty();	
	private ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>();
	private BooleanProperty completedProperty = new SimpleBooleanProperty();
	
	private int id;
	
	public ToDo(int id, String title, LocalDate date, boolean completed) {
		this.id = id;
		setTitle(title);
		setDate(date);
		setCompleted(completed);
		
		titleProperty.addListener((observable, oldValue, newValue) -> {
			System.out.println("Title changed: [" + id + "] " + oldValue + " -> " + newValue);
		});
		dateProperty.addListener((observable, oldValue, newValue) -> {
			System.out.println("Date changed: [" + id + "] " + oldValue + " -> " + newValue);
		});
		completedProperty.addListener((observable, oldValue, newValue) -> {
			System.out.println("Completed changed: [" + id + "] " + oldValue + " -> " + newValue);
		});
	}

	public StringProperty getTitleProperty() {
		return titleProperty;
	}
	
	public ObjectProperty<LocalDate> getDateProperty() {
		return dateProperty;
	}
	
	public BooleanProperty getCompletedProperty() {
		return completedProperty;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return titleProperty.get();
	}
	public void setTitle(String title) {
		titleProperty.set(title);
	}
	public LocalDate getDate() {
		return dateProperty.get(); 
	}
	public void setDate(LocalDate localDate) {
		dateProperty.set(localDate);
	}
	public boolean isCompleted() {
		return completedProperty.get();
	}
	public void setCompleted(boolean completed) {
		completedProperty.set(completed);
	}
}