package com.example.todoback.strategy.sorting;

import com.example.todoback.models.TodoItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
public class PriorityDateSortStrategy implements SortStrategy {

    private boolean priorityDesc;
    private boolean dateDesc;


    @Override
    public void sort(List<TodoItem> todos) {

        Comparator<TodoItem> comparator = Comparator.comparing(TodoItem::getPriority)
                .thenComparingLong(TodoItem::translateDueDate);

        if(priorityDesc && dateDesc) {
            comparator = comparator.reversed();
        }
        else if(priorityDesc) {
            comparator = Comparator.comparing(TodoItem::getPriority).reversed()
                    .thenComparingLong(TodoItem::translateDueDate);
        }
        else if(dateDesc) {
            comparator = Comparator.comparing(TodoItem::getPriority)
                    .thenComparing(Comparator.comparingLong(TodoItem::translateDueDate).reversed());
        }

        todos.sort(comparator);
    }






}
