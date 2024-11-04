package com.example.todoback.strategy.sorting;

import com.example.todoback.models.TodoItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
public class PrioritySortStrategy implements SortStrategy {

    private boolean desc;

    @Override
    public void sort(List<TodoItem> todos) {


        if(this.desc){
            todos.sort(Comparator.comparing(TodoItem::getPriorityValue));
        }
        else{
            todos.sort(Comparator.comparing(TodoItem::getPriorityValue).reversed());
        }
    }
}
