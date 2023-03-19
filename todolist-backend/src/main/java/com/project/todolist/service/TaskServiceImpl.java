package com.project.todolist.service;

import com.project.todolist.dto.TaskDto;
import com.project.todolist.entity.Task;
import com.project.todolist.entity.enums.Status;
import com.project.todolist.model.DeadlineDateTime;
import com.project.todolist.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    private static final String ERROR = "Task not found with id ";

    public void updateRepo() {
        Iterable<Task> tasks = taskRepository.findAll();

        for (Task task : tasks) {
            if (
                    task.getStatus().equals(Status.IN_PROGRESS)
                            && LocalDateTime.now()
                                    .isAfter(task.getDateOfDeadline())
            ) {
                task.setStatus(Status.FAILED);
            }
        }

        taskRepository.saveAll(tasks);
    }

    @Override
    public void delete(long id) {
        updateRepo();
        Task task = taskRepository
                .findById(id)
                .orElseThrow(
                        () -> new HttpClientErrorException(
                                HttpStatus.NO_CONTENT,
                                ERROR + id
                        )
                );
        taskRepository.deleteById(task.getId());
    }

    @Override
    public Task create(TaskDto taskDto) {
        updateRepo();

        Task task = taskDto.getTask();

        if (taskDto.getTask().getDateOfCreated() == null) {
            task.init();
        }

        DeadlineDateTime time = taskDto.getTime();

        task.setDateOfDeadline(LocalDateTime.of(
                time.getYear(),
                time.getMonth(),
                time.getDay(),
                time.getHours(),
                time.getMinutes()
        ));

        task.setStatus(Status.IN_PROGRESS);
        if (task.getStatus().equals(Status.IN_PROGRESS)
                && LocalDateTime.now().isAfter(task.getDateOfDeadline())) {
            task.setStatus(Status.FAILED);
        }

        return taskRepository.save(task);
    }

    @Override
    public Task update(long id, TaskDto taskDto) {
        updateRepo();
        Task updatedTask = taskRepository
                .findById(id)
                .orElseThrow(
                        () -> new HttpClientErrorException(
                                HttpStatus.NOT_FOUND,
                                ERROR + id
                        )
                );

        if (!updatedTask.getStatus().equals(Status.IN_PROGRESS)) {
            throw new HttpClientErrorException(
                    HttpStatus.OK,
                    "Task with id " + id + " is "
                            +  updatedTask.getStatus().name().toLowerCase()
            );
        }

        DeadlineDateTime time = taskDto.getTime();

        if (time != null) {
            updatedTask.setDateOfDeadline(LocalDateTime.of(
                    time.getYear(),
                    time.getMonth(),
                    time.getDay(),
                    time.getHours(),
                    time.getMinutes()
            ));
        }
        updatedTask.setName(taskDto.getTask().getName());
        if (taskDto.getTask().getDescription() != null) {
            updatedTask.setDescription(taskDto.getTask().getDescription());
        }
        return taskRepository.save(updatedTask);
    }

    @Override
    public Task resolve(long id) {
        updateRepo();

        Task task = taskRepository
                .findById(id)
                .orElseThrow(
                        () -> new HttpClientErrorException(
                                HttpStatus.NOT_FOUND,
                                ERROR + id
                        )
                );

        if (!task.getStatus().equals(Status.IN_PROGRESS)) {
            throw new HttpClientErrorException(
                    HttpStatus.OK,
                    "Task with id " + id + " is "
                            +  task.getStatus().name().toLowerCase()
            );
        }

        task.setStatus(Status.RESOLVED);
        return taskRepository.save(task);
    }

    @Override
    public Task getById(long id) {
        updateRepo();
        return taskRepository
                .findById(id)
                .orElseThrow(
                        () -> new HttpClientErrorException(
                                HttpStatus.NOT_FOUND,
                                ERROR + id
                        )
                );
    }

    @Override
    public List<Task> getAll() {
        updateRepo();
        return taskRepository.findAllByOrderByIdDesc();
    }
}
