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

        Comparator<TodoItem> comparator = Comparator.comparing(TodoItem::getPriority);

        if (Objects.equals(priorityDesc, "DESC")) {
            comparator = comparator.reversed();
        }

        if (Objects.equals(dateDesc, "DESC")) {
            comparator = comparator.thenComparing(Comparator.comparingLong(TodoItem::translateDueDate).reversed());
        } else {
            comparator = comparator.thenComparingLong(TodoItem::translateDueDate);
        }


        todos.sort(comparator);
    }






}
