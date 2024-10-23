package com.example.todoback.repository;

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

    private ArrayList<TodoItem> filterItems(String textFilter, List<PriorityLevel> priorityFilter, String stateFilter) {

        return todoItems.stream()
                .filter(todoItem -> textFilter.isEmpty() || todoItem.getText().toLowerCase().contains(textFilter.toLowerCase()))
                .filter(todoItem -> priorityFilter.isEmpty() || priorityFilter.contains(todoItem.getPriority()))
                .filter(todoItem -> stateFilter.isEmpty() || todoItem.isDone() == stateFilter.equals("done"))
                .collect(Collectors.toCollection(ArrayList::new));

    }

    private ArrayList<TodoItem> sortItems(ArrayList<TodoItem> filteredItems,
                                          List<String> sortBy,
                                          String priorityOrder,
                                          String duedateOrder) {

        boolean prioritySortDesc = priorityOrder.equals("DESC");
        boolean dueDateSortDesc = duedateOrder.equals("DESC");



        String key = String.join(",", sortBy);

        Map<String, SortStrategy> strategies = new HashMap<>();

        strategies.put("", new DefaultSortStrategy(prioritySortDesc));
        strategies.put("priority,duedate", new PriorityDateSortStrategy(priorityOrder, duedateOrder));
        strategies.put("priority", new PrioritySortStrategy(prioritySortDesc));
        strategies.put("duedate", new DueDateSortStrategy(dueDateSortDesc));

        strategies.get(key).sort(filteredItems);


        return filteredItems;
    }

    public ArrayList<TodoItem> getAll() { return todoItems; }

    public PaginatedTodoDTO getAllPaginated(int page,
                                            String textFilter,
                                            List<String> sortBy,
                                            String priorityOrder,
                                            String dueDateOrder,
                                            List<PriorityLevel> priorityFilter,
                                            String stateFilter ) {

        ArrayList<TodoItem> filteredItems = filterItems(textFilter, priorityFilter, stateFilter);

        sortItems(filteredItems, sortBy, priorityOrder, dueDateOrder);


        GetRequestParamsDTO params = GetRequestParamsDTO.builder()
                            .textFilter(textFilter)
                            .priorityFilter(priorityFilter)
                            .stateFilter(stateFilter)
                            .sortBy(sortBy)
                            .prioritySortOrder(priorityOrder)
                            .dueDateSortOrder(dueDateOrder)
                            .build();



        PaginatedTodoDTO dto = new PaginatedTodoDTO(filteredItems, page, params);

        dto.handleAverages(todoItems);

        dto.checkAllDone(filteredItems);

        return dto;

    }

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

    public TodoItem checkById(String id) {
        for(TodoItem item : todoItems){
            if(item.getId().equals(id)){
                item.CheckDone();
                return item;
            }
        }

        return null;
    }

    public TodoItem uncheckById(String id) {
        for(TodoItem item : todoItems){
            if(item.getId().equals(id)){
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
