package com.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToDoManager {
	private ArrayList<ToDo> todos;
	
	public List<ToDo> getToDos() {
		return todos;
	}
	
	public void remove(ToDo todo) {
		todos.remove(todo);
		System.out.println("Removed: " + todo.getId());
	}
	
	public ToDo create(String title, LocalDate date) {
		int newId;
		if (todos.size() > 0)
			newId = todos.stream().max((todo1, todo2) -> todo1.getId() - todo2.getId()).get().getId() + 1;
		else
			newId = 0;

		var newToDo = new ToDo(newId, title, date, false);
		todos.add(newToDo);

		System.out.println("Added: " + newId);

		return newToDo;
	}

	public ToDoManager() {
		todos = new ArrayList<>(List.of(
				new ToDo(1, "Design", LocalDate.parse("2022-12-01"), true),
				new ToDo(2, "Implementation", LocalDate.parse("2022-12-07"), false)));
	}

}
