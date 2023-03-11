package com.project.todolist.repository;

import com.project.todolist.entity.DayTask;
import org.springframework.data.repository.CrudRepository;

public interface DayTaskRepository extends CrudRepository<DayTask, Long> {
}
