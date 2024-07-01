package com.example.todoback.repository;

import com.example.todoback.models.TodoItem;

import java.util.ArrayList;

public class TodoRepository {

    private ArrayList<TodoItem> todoItems;

    public TodoRepository() { todoItems = new ArrayList<TodoItem>(); }

    public ArrayList<TodoItem> getAll() { return todoItems; }

    public TodoItem getById(long id) {

        return todoItems.stream()
                .filter(x -> x.id() == id)
                .findFirst()
                .orElse(null);
    }

    public TodoItem add(TodoItem todoItem) {
        todoItems.add(todoItem);
        return todoItem;
    }
}
