package com.project.todolist.controller;

import com.project.todolist.entity.DayTask;
import com.project.todolist.service.DayTaskServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DayTaskController {

    private DayTaskServiceImpl taskService;

    @GetMapping("/day")
    public DayTask getDayTask() {
        return taskService.getRandom();
    }
}

