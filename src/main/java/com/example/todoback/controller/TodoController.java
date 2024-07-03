package com.example.todoback.controller;

import com.example.todoback.enums.PriorityLevel;
import com.example.todoback.models.DTOs.PaginatedTodoDTO;
import com.example.todoback.models.TodoItem;
import com.example.todoback.models.DTOs.TodoItemDTO;
import com.example.todoback.repository.TodoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/api")
public class TodoController {

    private final TodoRepository repo;

    public TodoController() {
        repo = new TodoRepository();
    }

    @GetMapping("/todos")
    public ResponseEntity<PaginatedTodoDTO> getAllPaginated(@RequestParam @Min(1) int page) {
        return ResponseEntity.ok(repo.getAllPaginated(page));
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
                    .priority(PriorityLevel.High)
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
        return ResponseEntity.ok(repo.checkById(id));
    }

    @PutMapping("/todos/{id}/undone")
    public ResponseEntity<TodoItem>  uncomplete(@PathVariable String id) {
        return ResponseEntity.ok(repo.uncheckById(id));
    }

}
