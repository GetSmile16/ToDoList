package com.project.todolist.repository;

import com.project.todolist.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAllByOrderByIdDesc();
}
