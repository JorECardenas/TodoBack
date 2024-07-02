package com.example.todoback.repository;

import com.example.todoback.models.DTOs.TodoItemDTO;
import com.example.todoback.models.TodoItem;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class TodoRepository {

    private final ArrayList<TodoItem> todoItems;

    public TodoRepository() { todoItems = new ArrayList<TodoItem>(); }

    public ArrayList<TodoItem> getAll() { return todoItems; }

    public TodoItem getById(String id) {

        return todoItems.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public TodoItem add(TodoItemDTO dto) {

        TodoItem item = new TodoItem(dto);

        todoItems.add(item);


        return item;
    }

    public TodoItem update(TodoItemDTO dto, String id) {

        for(TodoItem item : todoItems){
            if(item.getId().equals(id)){
                item.update(dto);
                return item;
            }
        }

        return null;

    }

    public TodoItem remove(String id) {
        for(TodoItem item : todoItems){
            if(item.getId().equals(id)){
                todoItems.remove(item);
                return item;
            }
        }

        return null;
    }

}
