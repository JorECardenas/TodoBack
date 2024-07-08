package com.example.todoback.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@AllArgsConstructor
@Builder
public class AverageDataDTO {

    private long generalAverage;

    private long lowAverage;

    private long mediumAverage;

    private long highAverage;



}
