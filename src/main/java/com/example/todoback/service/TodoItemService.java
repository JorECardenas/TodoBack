package com.example.todoback.service;

import com.example.todoback.enums.PriorityLevel;
import com.example.todoback.models.DTOs.GetRequestParamsDTO;
import com.example.todoback.models.DTOs.PaginatedTodoDTO;
import com.example.todoback.models.DTOs.TodoItemDTO;
import com.example.todoback.models.TodoItem;
import com.example.todoback.repository.TodoItemsRepo;
import com.example.todoback.strategy.sorting.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TodoItemService {

    TodoItemsRepo repo;

    @Autowired
    public TodoItemService(TodoItemsRepo repo) {
        this.repo = repo;
    }

    private ArrayList<TodoItem> filterItems(List<TodoItem> todoItems, String textFilter, List<PriorityLevel> priorityFilter, String stateFilter) {

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

    public PaginatedTodoDTO getAllPaginated(int page,
                                            String textFilter,
                                            List<String> sortBy,
                                            String priorityOrder,
                                            String dueDateOrder,
                                            List<PriorityLevel> priorityFilter,
                                            String stateFilter ) {

        List<TodoItem> todoItems = repo.findAll();


        ArrayList<TodoItem> filteredItems = filterItems(todoItems, textFilter, priorityFilter, stateFilter);

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

    public List<TodoItem> getAll(){
        return repo.findAll();
    }

    public TodoItem getById(Long id){
        return repo.findById(id).orElse(null);
    }

    public TodoItem add(TodoItemDTO dto){
        TodoItem item = new TodoItem(dto);
        repo.save(item);
        return item;
    }

    public TodoItem update(TodoItemDTO dto, Long id){
        TodoItem item = repo.findById(id).orElse(null);
        if(item != null){
            item.update(dto);
            repo.save(item);
        }
        return item;
    }

    public TodoItem remove(Long id){
        TodoItem item = repo.findById(id).orElse(null);
        if(item != null){
            repo.delete(item);
        }
        return item;
    }

    public TodoItem checkById(Long id){
        TodoItem item = repo.findById(id).orElse(null);
        if(item != null){
            item.CheckDone();
            repo.save(item);
        }
        return item;
    }

    public TodoItem uncheckById(Long id){
        TodoItem item = repo.findById(id).orElse(null);
        if(item != null){
            item.CheckUndone();
            repo.save(item);
        }
        return item;
    }

    public List<TodoItem> checkAll(GetRequestParamsDTO params){
        List<TodoItem> todoItems = repo.findAll();

        ArrayList<TodoItem> filteredItems = filterItems(todoItems, params.getTextFilter(), params.getPriorityFilter(), params.getStateFilter());

        for(TodoItem item : filteredItems){
            item.CheckDone();
        }

        repo.saveAll(filteredItems);

        return filteredItems;
    }

    public List<TodoItem> uncheckAll(GetRequestParamsDTO params){
        List<TodoItem> todoItems = repo.findAll();

        ArrayList<TodoItem> filteredItems = filterItems(todoItems, params.getTextFilter(), params.getPriorityFilter(), params.getStateFilter());

        for(TodoItem item : filteredItems){
            item.CheckUndone();
        }

        repo.saveAll(filteredItems);

        return filteredItems;
    }




}
