package com.example.todoback.service;

import com.example.todoback.enums.PriorityLevel;
import com.example.todoback.models.DTOs.GetRequestParamsDTO;
import com.example.todoback.models.DTOs.PaginatedTodoDTO;
import com.example.todoback.models.DTOs.TodoItemDTO;
import com.example.todoback.models.TodoItem;
import com.example.todoback.strategy.sorting.*;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

import java.util.*;


@Repository
public class TodoRepository {

    private final ArrayList<TodoItem> todoItems;

    public TodoRepository() { todoItems = new ArrayList<>(); }

    public ArrayList<TodoItem> getAll() { return todoItems; }



    public TodoItem getById(Long id) {

        return todoItems.stream()
                .filter(x -> Objects.equals(x.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public TodoItem add(TodoItemDTO dto) {

        TodoItem item = new TodoItem(dto);

        todoItems.add(item);


        return item;
    }

    public TodoItem update(TodoItemDTO dto, Long id) {

        for(TodoItem item : todoItems){
            if(Objects.equals(item.getId(), id)){
                item.update(dto);
                return item;
            }
        }

        return null;

    }

    public TodoItem remove(Long id) {
        for(TodoItem item : todoItems){
            if(Objects.equals(item.getId(), id)){
                todoItems.remove(item);
                return item;
            }
        }

        return null;
    }

    public TodoItem checkById(Long id) {
        for(TodoItem item : todoItems){
            if(Objects.equals(item.getId(), id)){
                item.CheckDone();
                return item;
            }
        }

        return null;
    }

    public TodoItem uncheckById(Long id) {
        for(TodoItem item : todoItems){
            if(Objects.equals(item.getId(), id)){
                item.CheckUndone();
                return item;
            }
        }

        return null;
    }

    public List<TodoItem> checkAll(GetRequestParamsDTO params) {
        for (TodoItem item : todoItems) {
            if(item.Filtered(params)){
                item.CheckDone();

            }
        }

        return todoItems;
    }

    public List<TodoItem> uncheckAll(GetRequestParamsDTO params) {
        for (TodoItem item : todoItems) {
            if(item.Filtered(params)){
                item.CheckUndone();

            }

        }

        return todoItems;
    }

}
