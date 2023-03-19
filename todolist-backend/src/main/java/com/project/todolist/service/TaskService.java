package com.project.todolist.service;

import com.project.todolist.dto.TaskDto;
import com.project.todolist.entity.Task;

import java.util.List;

public interface TaskService {
    void delete(long id);
    Task create(TaskDto taskDto);
    Task update(long id, TaskDto taskDto);
    Task getById(long id);
    List<Task> getAll();
    Task resolve(long id);
}
