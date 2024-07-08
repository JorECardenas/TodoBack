package com.example.todoback.strategy.sorting;

import com.example.todoback.models.TodoItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class PriorityDateSortStrategy implements SortStrategy {

    private String priorityDesc;
    private String dateDesc;


    @Override
    public void sort(List<TodoItem> todos) {

        Comparator<TodoItem> comparator = Comparator.comparing(TodoItem::getPriority)
                .thenComparingLong(TodoItem::translateDueDate);

        if(Objects.equals(priorityDesc, "DESC") && Objects.equals(dateDesc, "DESC")) {
            comparator = Comparator.comparing(TodoItem::getPriority)
                    .thenComparing(Comparator.comparingLong(TodoItem::translateDueDate).reversed());
        }
        else if(Objects.equals(priorityDesc, "DESC") && Objects.equals(dateDesc, "ASC")) {
            comparator = Comparator.comparing(TodoItem::getPriority)
                    .thenComparingLong(TodoItem::translateDueDate);
        }
        else if(Objects.equals(priorityDesc, "ASC") && Objects.equals(dateDesc, "DESC")) {
            comparator = Comparator.comparing(TodoItem::getPriority).reversed()
                    .thenComparing(Comparator.comparingLong(TodoItem::translateDueDate).reversed());
        }
        else if(Objects.equals(priorityDesc, "ASC") && Objects.equals(dateDesc, "ASC")) {
            comparator = Comparator.comparing(TodoItem::getPriority).reversed()
                    .thenComparing(Comparator.comparingLong(TodoItem::translateDueDate));
        }


        todos.sort(comparator);
    }






}
