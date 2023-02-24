package com.project.todolist.repositories;

import com.project.todolist.domains.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAllByOrderByIdDesc();
}
