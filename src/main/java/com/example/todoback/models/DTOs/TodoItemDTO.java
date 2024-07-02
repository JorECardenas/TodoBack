package com.example.todoback.models.DTOs;

import com.example.todoback.enums.PriorityLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class TodoItemDTO {
    private String text;
    private boolean done;
    private PriorityLevel priority;
    private Date dueDate;
    private Date doneDate;

    public TodoItemDTO(String text, boolean done, PriorityLevel priority, Date dueDate, Date doneDate) {
        this.text = text;
        this.done = done;
        this.priority = priority;
        this.dueDate = dueDate;
        this.doneDate = doneDate;
    }

}
