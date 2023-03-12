package com.project.todolist.dto;

import com.project.todolist.entity.Task;
import com.project.todolist.model.DeadlineDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Task task;
    private DeadlineDateTime time;
}
