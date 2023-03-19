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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tasks")
public class TaskController {
    private TaskServiceImpl taskService;

    @CrossOrigin
    @GetMapping()
    public List<Task> getTasks() {
        return taskService.getAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public Task getTask(@PathVariable long id) {
        return taskService.getById(id);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public Task editTask(@PathVariable long id,
                                         @RequestBody TaskDto taskDto) {
        return taskService.update(id, taskDto);
    }

    @CrossOrigin
    @PutMapping("/resolve/{id}")
    public Task resolveTask(@PathVariable long id) {
        return taskService.resolve(id);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/create")
    public Task addTask(@RequestBody TaskDto taskDto) {
        return taskService.create(taskDto);
    }
}
