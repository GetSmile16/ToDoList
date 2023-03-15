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
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/tasks")
public class TaskController {

    private TaskServiceImpl taskService;

    @GetMapping()
    public List<Task> getTasks() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable long id) {
        return taskService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> editTask(@PathVariable long id,
                                         @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.update(id, taskDto));
    }

    @PutMapping("/resolve/{id}")
    public ResponseEntity<Task> resolveTask(@PathVariable long id) {
        taskService.resolve(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public Task addTask(@RequestBody TaskDto taskDto) {
        return taskService.create(taskDto);
    }
}
