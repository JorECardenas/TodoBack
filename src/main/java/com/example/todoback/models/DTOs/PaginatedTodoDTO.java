package com.example.todoback.models.DTOs;

import com.example.todoback.models.TodoItem;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginatedTodoDTO {

    private static int PAGE_SIZE = 10;

    private List<TodoItem> content;

    private GetRequestParamsDTO parameters;

    private boolean isLastPage;
    private boolean isFirstPage;

    private int currentPage;

    private int itemsInPage;

    private int totalPages;
    private int totalItems;



    public PaginatedTodoDTO(ArrayList<TodoItem> original, int pageNumber, GetRequestParamsDTO parameters) {

        int start = (pageNumber - 1) * PAGE_SIZE;
        int end = Math.min((start + PAGE_SIZE), original.size());

        this.content = original.subList(start, end);

        this.itemsInPage = content.size();

        this.currentPage = pageNumber;

        this.totalItems = original.size();
        this.totalPages = (int) Math.ceil((float) totalItems / (float) PAGE_SIZE);

        this.isFirstPage = start == 0;
        this.isLastPage = currentPage == totalPages;


        this.parameters = parameters;
    }




}
