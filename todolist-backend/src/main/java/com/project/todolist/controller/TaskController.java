package com.project.todolist.controller;

import com.project.todolist.dto.TaskDto;
import com.project.todolist.entity.Task;
import com.project.todolist.service.TaskServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
public class TaskController {

    private TaskServiceImpl taskService;

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return taskService.getAll();
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable long id) {
        return taskService.getById(id);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> editTask(@PathVariable long id,
                                         @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.update(id, taskDto));
    }

    @PutMapping("/tasks/resolve/{id}")
    public ResponseEntity<Task> resolveTask(@PathVariable long id) {
        taskService.resolve(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tasks/create")
    public Task addTask(@RequestBody TaskDto taskDto) {
        return taskService.create(taskDto);
    }
}
