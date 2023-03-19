package com.project.todolist.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeadlineDateTime {
    private int day;
    private int month;
    private int year;
    private int hours;
    private int minutes;

}
