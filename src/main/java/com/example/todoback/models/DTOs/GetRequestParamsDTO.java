package com.example.todoback.models.DTOs;


import com.example.todoback.enums.PriorityLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.PriorityQueue;

@Data
@Builder
@AllArgsConstructor
public class GetRequestParamsDTO {

    private String textFilter;
    private List<PriorityLevel> priorityFilter;
    private String stateFilter;

    private List<String> sortBy;

    private String sortOrder;





}
