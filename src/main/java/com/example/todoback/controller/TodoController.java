package com.example.todoback.controller;

import com.example.todoback.models.TodoItem;
import com.example.todoback.models.DTOs.TodoItemDTO;
import com.example.todoback.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class TodoController {

    private final TodoRepository repo;

    public TodoController() {
        repo = new TodoRepository();
    }

    @GetMapping("/todo")
    public ArrayList<TodoItem> getAll() { return repo.getAll(); }

    @GetMapping("/todo/{id}")
    public TodoItem getById(@PathVariable String id) { return repo.getById(id); }


    @PostMapping("/todo")
    public TodoItem create(@RequestBody TodoItemDTO dto) { return repo.add(dto); }

    @PostMapping("/todo/{id}")
    public TodoItem update(@RequestBody TodoItemDTO todoItem, @PathVariable String id) { return repo.update(todoItem, id); }

    @DeleteMapping("/todo/{id}")
    public TodoItem delete(@PathVariable String id) { return repo.remove(id);}

}
