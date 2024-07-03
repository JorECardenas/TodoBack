package com.example.todoback.models.DTOs;

import com.example.todoback.enums.PriorityLevel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


import java.util.Date;

@Data
@Builder
public class TodoItemDTO {

    @NotNull
    @Size(min = 1, max = 120)
    private String text;

    @NotNull
    private boolean done;

    @NotNull
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
