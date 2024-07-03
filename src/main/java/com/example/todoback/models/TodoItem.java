package com.example.todoback.models;

import com.example.todoback.enums.PriorityLevel;
import com.example.todoback.models.DTOs.TodoItemDTO;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;



@Data
public class TodoItem {

    private String id;


    private String text;
    private Date dueDate;
    private boolean done;
    private Date doneDate;
    private PriorityLevel priority;
    private Date creationDate;

    public int translatePriorityValue(){
        switch (priority){
            case High -> {
                return 0;
            }
            case Medium -> {
                return 1;
            }
            case Low -> {
                return 2;
            }
        }

        return -1;
    }

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


    public void CheckDone(){
        if(!this.done){
            this.done = true;

            this.doneDate = Calendar.getInstance().getTime();
        }
    }

    public void CheckUndone(){
        if(this.done){
            this.done = false;

            this.doneDate = null;
        }
    }

}
