package com.example.todoback.models;

import com.example.todoback.enums.PriorityLevel;
import com.example.todoback.models.DTOs.GetRequestParamsDTO;
import com.example.todoback.models.DTOs.TodoItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String text;
    private Date dueDate;
    private boolean done;
    private Date doneDate;
    private PriorityLevel priority;
    private Date creationDate;


    public int getPriorityValue(){
        return priority.getValue();
    }

    public long translateDueDate(){

        if (dueDate == null){
            return 0;
        }

        return dueDate.getTime();


    }

    public TodoItem(String text, boolean done, Date dueDate, Date doneDate, PriorityLevel priority) {

        this.text = text;
        this.done = done;
        this.dueDate = dueDate;
        this.doneDate = doneDate;
        this.priority = priority;

        this.creationDate = Calendar.getInstance().getTime();

    }

    public TodoItem(TodoItemDTO dto) {

        this.text = dto.getText();
        this.dueDate = dto.getDueDate();
        this.done = dto.isDone();
        this.doneDate = dto.getDoneDate();
        this.priority = dto.getPriority();


        this.creationDate = Calendar.getInstance().getTime();
    }

    public boolean Filtered(GetRequestParamsDTO params){

        boolean textFilterMatch = params.getTextFilter().isEmpty() || this.text.contains(params.getTextFilter());
        boolean priorityFilterMatch = params.getPriorityFilter().isEmpty() || params.getPriorityFilter().contains(this.priority);
        boolean stateFilterMatch = params.getStateFilter().isEmpty() || params.getStateFilter().equals("done") == this.done;

        return textFilterMatch && priorityFilterMatch && stateFilterMatch;
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
