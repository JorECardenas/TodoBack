package com.example.todoback.controller;

import com.example.todoback.models.TodoItem;
import com.example.todoback.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/api")
public class TodoController {

    private TodoRepository repo;

    public TodoController() {
        repo = new TodoRepository();
    }

    @GetMapping("/todo")
    public ArrayList<TodoItem> getAll() { return repo.getAll(); }

    @GetMapping("/todo/{id}")
    public TodoItem getById(@PathVariable Long id) { return repo.getById(id); }


    @PostMapping("/todo")
    public TodoItem create(@RequestBody TodoItem todoItem) { return repo.add(todoItem); }

}
