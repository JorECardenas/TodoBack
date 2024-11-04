package com.example.todoback.models.DTOs;

import com.example.todoback.models.TodoItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginatedTodoDTO {

    private static int PAGE_SIZE = 10;

    private List<TodoItem> content;

    private GetRequestParamsDTO parameters;

    private AverageDataDTO averageData;

    private boolean isLastPage;
    private boolean isFirstPage;

    private int currentPage;

    private int itemsInPage;

    private int totalPages;
    private int totalItems;

    private int completedItems;

    private boolean allDone;



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

        this.completedItems = (int) original.stream().filter(TodoItem::isDone).count();

        this.parameters = parameters;
    }

    public void checkAllDone(List<TodoItem> items) {

        this.allDone =  !items.isEmpty() && items.stream().allMatch(TodoItem::isDone);

    }

    public void handleAverages(List<TodoItem> items) {
        int numLows = 0;
        int numMediums = 0;
        int numHighs = 0;

        long lowDuration = 0;
        long mediumDuration = 0;
        long highDuration = 0;

        long totalDuration = 0;
        long doneItems = 0;

        for (TodoItem item : items) {

            if(!item.isDone()) {
                continue;
            }

            if(item.getDueDate() == null || item.getDoneDate() == null){
                continue;
            }

            switch (item.getPriority()){
                case High -> {
                    numHighs++;
                    highDuration += item.getDoneDate().getTime() - item.getCreationDate().getTime();
                }
                case Medium -> {
                    numMediums++;
                    mediumDuration += item.getDoneDate().getTime() - item.getCreationDate().getTime();
                }
                case Low -> {
                    numLows++;
                    lowDuration += item.getDoneDate().getTime() - item.getCreationDate().getTime();
                }
            }

            totalDuration += item.getDoneDate().getTime() - item.getCreationDate().getTime();
            doneItems ++;

        }

        long generalAverage = doneItems == 0 ? 0 : totalDuration / doneItems;

        long lowAverage = numLows == 0 ? 0 : lowDuration / numLows;
        long mediumAverage = numMediums == 0 ? 0 : mediumDuration / numMediums  ;
        long highAverage = numHighs == 0 ? 0 : highDuration / numHighs;



        this.averageData = new AverageDataDTO(generalAverage, lowAverage, mediumAverage, highAverage);




    }




}
