package com.example.todoback.models.DTOs;

import com.example.todoback.models.TodoItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginatedTodoDTO {

    private static int PAGE_SIZE = 10;

    private List<TodoItem> content;

    private boolean isLastPage;
    private boolean isFirstPage;

    private int currentPage;

    private int totalPages;
    private int totalItems;



    public PaginatedTodoDTO(ArrayList<TodoItem> original, int pageNumber) {

        int start = (pageNumber - 1) * PAGE_SIZE;
        int end = start + PAGE_SIZE;

        this.content = original.subList(start, end);

        this.currentPage = pageNumber;

        this.totalItems = original.size();
        this.totalPages = totalItems / PAGE_SIZE;

        this.isFirstPage = start == 0;
        this.isLastPage = currentPage == totalPages;
    }




}
