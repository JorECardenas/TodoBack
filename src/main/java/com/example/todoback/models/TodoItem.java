package com.example.todoback.models;

import com.example.todoback.enums.PriorityLevel;
import com.example.todoback.models.DTOs.TodoItemDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

//public record TodoItem( long id,
//                        String text,
//                        Date dueDate,
//                        boolean done,
//                        Date doneDate,
//                        PriorityLevel priority,
//                        Date creationDate) {
//
//}

@Getter
@Setter
public class TodoItem {
    private String id;
    private String text;
    private Date dueDate;
    private boolean done;
    private Date doneDate;
    private PriorityLevel priority;
    private Date creationDate;

    public TodoItem(String text, boolean done, Date dueDate, Date doneDate, PriorityLevel priority) {
        this.id = UUID.randomUUID().toString();

        this.text = text;
        this.done = done;
        this.dueDate = dueDate;
        this.doneDate = doneDate;
        this.priority = priority;

        this.creationDate = Calendar.getInstance().getTime();

    }

    public TodoItem(TodoItemDTO dto) {
        this.id = UUID.randomUUID().toString();

        this.text = dto.getText();
        this.dueDate = dto.getDueDate();
        this.done = dto.isDone();
        this.doneDate = dto.getDoneDate();
        this.priority = dto.getPriority();

        this.creationDate = Calendar.getInstance().getTime();
    }


    public void update(TodoItemDTO dto){
        this.text = dto.getText();
        this.dueDate = dto.getDueDate();
        this.done = dto.isDone();
        this.doneDate = dto.getDoneDate();
        this.priority = dto.getPriority();
    }

}
