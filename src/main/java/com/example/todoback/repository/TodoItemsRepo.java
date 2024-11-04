package com.example.todoback.repository;

import com.example.todoback.models.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemsRepo extends JpaRepository<TodoItem, Long> {
}
