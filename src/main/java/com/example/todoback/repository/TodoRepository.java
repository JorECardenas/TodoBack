package com.example.todoback.repository;

import com.example.todoback.enums.PriorityLevel;
import com.example.todoback.models.DTOs.GetRequestParamsDTO;
import com.example.todoback.models.DTOs.PaginatedTodoDTO;
import com.example.todoback.models.DTOs.TodoItemDTO;
import com.example.todoback.models.TodoItem;
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

    private ArrayList<TodoItem> sortItems(ArrayList<TodoItem> filteredItems, List<String> sortBy, String sortOrder) {
        boolean sortDesc = sortOrder.equals("DESC");

        //System.out.println("sortOrder: " + sortOrder + ", sortDesc: " + sortBy.toString());

        if(sortBy.isEmpty()) {
            filteredItems.sort(Comparator.comparing(TodoItem::getCreationDate));
        }
        else if (sortBy.contains("priority") && sortBy.contains("duedate")) {
            filteredItems.sort(Comparator.comparing(TodoItem::translatePriorityValue)
                    .thenComparing(TodoItem::getDueDate));
        }
        else if(sortBy.contains("priority")) {
            filteredItems.sort(Comparator.comparing(TodoItem::translatePriorityValue));
        }
        else if(sortBy.contains("duedate")) {
            filteredItems.sort(Comparator.comparing(TodoItem::getDueDate));
        }


        if(!sortDesc) {
            Collections.reverse(filteredItems);
        }



        return filteredItems;
    }

    public ArrayList<TodoItem> getAll() { return todoItems; }

    public PaginatedTodoDTO getAllPaginated(int page,
                                            String textFilter,
                                            List<String> sortBy,
                                            String sortOrder,
                                            List<PriorityLevel> priorityFilter,
                                            String stateFilter ) {

        ArrayList<TodoItem> filteredItems = filterItems(textFilter, priorityFilter, stateFilter);

        filteredItems = sortItems(filteredItems, sortBy, sortOrder);


        GetRequestParamsDTO params = GetRequestParamsDTO.builder()
                            .textFilter(textFilter)
                            .priorityFilter(priorityFilter)
                            .stateFilter(stateFilter)
                            .sortBy(sortBy)
                            .sortOrder(sortOrder)
                            .build();


        return new PaginatedTodoDTO(filteredItems, page, params);
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

}
