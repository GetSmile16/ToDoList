package com.project.todolist.services;

import com.project.todolist.domains.Task;

import java.util.List;

public interface TaskService {
    void delete(long id);
    Task create(Task task, int days, int hours, int minutes);
    Task update(long id, Task task, int days, int hours, int minutes);
    Task getById(long id);
    List<Task> getAll();
}
