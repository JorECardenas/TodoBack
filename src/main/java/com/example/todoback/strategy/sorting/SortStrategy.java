package com.example.todoback.strategy.sorting;

import com.example.todoback.models.TodoItem;

import java.util.List;

public interface SortStrategy {

    public void sort(List<TodoItem> todos);
}
