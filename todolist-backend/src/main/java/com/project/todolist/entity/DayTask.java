package com.project.todolist.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "day_tasks")
public class DayTask extends TaskTemplate {
    public DayTask(String name) {
        this.name = name;
    }
}
