package com.example.todoback.models;

import com.example.todoback.enums.PriorityLevel;

import java.util.Date;

public record TodoItem(long id,
                        String text,
                        Date dueDate,
                        boolean done,
                        Date doneDate,
                        PriorityLevel priority,
                        Date creationDate) {
}
