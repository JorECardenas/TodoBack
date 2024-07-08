package com.example.todoback.controller;

import com.example.todoback.enums.PriorityLevel;
import com.example.todoback.models.DTOs.PaginatedTodoDTO;
import com.example.todoback.models.TodoItem;
import com.example.todoback.models.DTOs.TodoItemDTO;
import com.example.todoback.repository.TodoRepository;
import com.example.todoback.utils.RandomDateUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TodoController {

    private final TodoRepository repo;

    public TodoController() {
        repo = new TodoRepository();
    }

    @GetMapping("/todos")
    public ResponseEntity<PaginatedTodoDTO> getAllPaginated(@RequestParam(defaultValue = "1") @Min(1) int page,
                                                            @RequestParam(defaultValue = "") String textFilter,
                                                            @RequestParam(defaultValue = "") List<String> sortBy,
                                                            @RequestParam(defaultValue = "DESC") String priorityOrder,
                                                            @RequestParam(defaultValue = "DESC") String dueDateOrder,
                                                            @RequestParam(defaultValue = "") List<PriorityLevel> priorityFilter,
                                                            @RequestParam(defaultValue = "") String stateFilter) {
        return ResponseEntity.ok(repo.getAllPaginated(page, textFilter, sortBy, priorityOrder, dueDateOrder, priorityFilter, stateFilter));
    }

    @GetMapping("/todos/getAll")
    public ResponseEntity<ArrayList<TodoItem>> getAll() {
        return ResponseEntity.ok(repo.getAll());
    }

    @GetMapping("/fillData")
    public ResponseEntity<ArrayList<TodoItem>> fillData() {

        TodoItemDTO test;

        for (int i = 0; i < 20; i++) {



            test = TodoItemDTO.builder()
                    .text("test" + i)
                    .done(false)
                    .priority(PriorityLevel.values()[new Random().nextInt(PriorityLevel.values().length)])
                    .dueDate(RandomDateUtil.randomFutureDate())
                    .build();


            repo.add(test);
        }

        return ResponseEntity.ok(repo.getAll());

    }



    @GetMapping("/todos/{id}")
    public ResponseEntity<TodoItem> getById(@PathVariable String id) {
        return ResponseEntity.ok(repo.getById(id));
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoItem> create(@RequestBody @Valid TodoItemDTO dto) {
        return ResponseEntity.ok(repo.add(dto));
    }

    @PostMapping("/todos/{id}")
    public ResponseEntity<TodoItem>  update(@RequestBody @Valid TodoItemDTO todoItem, @PathVariable String id) {
        return ResponseEntity.ok(repo.update(todoItem, id));
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<TodoItem>  delete(@PathVariable String id) {
        return ResponseEntity.ok(repo.remove(id));
    }

    @PostMapping("/todos/{id}/done")
    public ResponseEntity<TodoItem>  complete(@PathVariable String id) {
        System.out.println(id);
        return ResponseEntity.ok(repo.checkById(id));
    }

    @PostMapping("/todos/{id}/undone")
    public ResponseEntity<TodoItem>  uncomplete(@PathVariable String id) {
        return ResponseEntity.ok(repo.uncheckById(id));
    }

    @PostMapping("/todos/all/done")
    public ResponseEntity<List<TodoItem>>  allDone() {
        return ResponseEntity.ok(repo.checkAll());
    }

    @PostMapping("/todos/all/undone")
    public ResponseEntity<List<TodoItem>>  allUndone() {
        return ResponseEntity.ok(repo.uncheckAll());
    }

}
