package com.example.todoback.controller;

import com.example.todoback.enums.PriorityLevel;
import com.example.todoback.models.DTOs.GetRequestParamsDTO;
import com.example.todoback.models.DTOs.PaginatedTodoDTO;
import com.example.todoback.models.TodoItem;
import com.example.todoback.models.DTOs.TodoItemDTO;
import com.example.todoback.service.TodoItemService;
import com.example.todoback.service.TodoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TodoController {

    private final TodoItemService service;

    @Autowired
    public TodoController(TodoItemService service) {
        this.service = service;
    }

    @GetMapping("/todos")
    public ResponseEntity<PaginatedTodoDTO> getAllPaginated(@RequestParam(defaultValue = "1") @Min(1) int page,
                                                            @RequestParam(defaultValue = "") String textFilter,
                                                            @RequestParam(defaultValue = "") List<String> sortBy,
                                                            @RequestParam(defaultValue = "DESC") String priorityOrder,
                                                            @RequestParam(defaultValue = "DESC") String dueDateOrder,
                                                            @RequestParam(defaultValue = "") List<PriorityLevel> priorityFilter,
                                                            @RequestParam(defaultValue = "") String stateFilter) {
        return ResponseEntity.ok(service.getAllPaginated(page, textFilter, sortBy, priorityOrder, dueDateOrder, priorityFilter, stateFilter));
    }

    @GetMapping("/todos/getAll")
    public ResponseEntity<List<TodoItem>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/fillData")
    public ResponseEntity<List<TodoItem>> fillData() {

        TodoItemDTO test;

        Calendar calendar = Calendar.getInstance();


        for (int i = 0; i < 15; i++) {
            test = TodoItemDTO.builder()
                    .text("test" + i)
                    .done(false)
                    .priority(PriorityLevel.values()[new Random().nextInt(PriorityLevel.values().length)])
                    .dueDate(calendar.getTime())
                    .build();

            calendar.add(Calendar.HOUR, 48);

            service.add(test);
        }

        return ResponseEntity.ok(service.getAll());

    }



    @GetMapping("/todos/{id}")
    public ResponseEntity<TodoItem> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoItem> create(@RequestBody @Valid TodoItemDTO dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    @PostMapping("/todos/{id}")
    public ResponseEntity<TodoItem>  update(@RequestBody @Valid TodoItemDTO todoItem, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(todoItem, id));
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<TodoItem>  delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.remove(id));
    }

    @PostMapping("/todos/{id}/done")
    public ResponseEntity<TodoItem>  complete(@PathVariable Long id) {
        return ResponseEntity.ok(service.checkById(id));
    }

    @PostMapping("/todos/{id}/undone")
    public ResponseEntity<TodoItem>  uncomplete(@PathVariable Long id) {
        return ResponseEntity.ok(service.uncheckById(id));
    }

    @PostMapping("/todos/all/done")
    public ResponseEntity<List<TodoItem>>  allDone(@RequestBody GetRequestParamsDTO params) {
        return ResponseEntity.ok(service.checkAll(params));
    }

    @PostMapping("/todos/all/undone")
    public ResponseEntity<List<TodoItem>>  allUndone(@RequestBody GetRequestParamsDTO params) {
        return ResponseEntity.ok(service.uncheckAll(params));
    }

}
