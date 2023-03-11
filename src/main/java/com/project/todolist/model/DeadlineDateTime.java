package com.project.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DeadlineDateTime {
    private int day;
    private int month;
    private int year;
    private int hours;
    private int minutes;

}
