package com.example.todoback.enums;

import lombok.Getter;

@Getter
public enum PriorityLevel {
    High(0),
    Medium(1),
    Low(2);

    private final int value;

    PriorityLevel(int value) {
        this.value = value;
    }

}
