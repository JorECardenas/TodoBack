package com.example.todoback.repository;

import com.example.todoback.enums.PriorityLevel;
import com.example.todoback.models.DTOs.GetRequestParamsDTO;
import com.example.todoback.models.DTOs.PaginatedTodoDTO;
import com.example.todoback.models.DTOs.TodoItemDTO;
import com.example.todoback.models.TodoItem;
import com.example.todoback.strategy.sorting.*;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class TodoRepository {

    private final ArrayList<TodoItem> todoItems;

    public TodoRepository() { todoItems = new ArrayList<>(); }

    private ArrayList<TodoItem> filterItems(String textFilter, List<PriorityLevel> priorityFilter, String stateFilter) {
        ArrayList<TodoItem> filteredItems = new ArrayList<>(todoItems);

        //System.out.println("tfilt: " + textFilter + ", pfilt: " + priorityFilter.toString() + ", sfilt: " + stateFilter.toString());

        if (!textFilter.isEmpty()) {
            filteredItems.removeIf(todoItem -> !todoItem.getText().toLowerCase().contains(textFilter.toLowerCase()));
        }

        if (!priorityFilter.isEmpty()) {
            filteredItems.removeIf(x -> !priorityFilter.contains(x.getPriority()));
        }

        if(!stateFilter.isEmpty()) {
            boolean done = stateFilter.equals("done");

            filteredItems.removeIf(x -> x.isDone() != done);
        }


        return filteredItems;

    }

    private ArrayList<TodoItem> sortItems(ArrayList<TodoItem> filteredItems,
                                          List<String> sortBy,
                                          String priorityOrder,
                                          String duedateOrder) {

        boolean prioritySortDesc = priorityOrder.equals("DESC");
        boolean dueDateSortDesc = duedateOrder.equals("DESC");



        String key = String.join(",", sortBy);

        //System.out.println(key);

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

        filteredItems = sortItems(filteredItems, sortBy, priorityOrder, dueDateOrder);


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

        dto.checkAllDone(todoItems);

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

    public List<TodoItem> checkAll() {
        for (TodoItem item : todoItems) {
            item.CheckDone();
        }

        return todoItems;
    }

    public List<TodoItem> uncheckAll() {
        for (TodoItem item : todoItems) {
            item.CheckUndone();
        }

        return todoItems;
    }

}
