package com.project.todolist.services;

import com.project.todolist.domains.Task;
import com.project.todolist.domains.enums.Status;
import com.project.todolist.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;

    private static final String ERROR = "Task not found with id ";

    public void updateRepo() {
        Iterable<Task> tasks = taskRepository.findAll();

        for (Task task : tasks) {
            if (task.getStatus().equals(Status.IN_PROGRESS)
                    && LocalDateTime.now().isAfter(task.getDateOfDeadline())) {
                task.setStatus(Status.FAILED);
            }
        }

        taskRepository.saveAll(tasks);
    }

    @Override
    public void delete(long id){
        updateRepo();
        Task task = taskRepository
                .findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, ERROR + id));
        taskRepository.deleteById(task.getId());
    }

    @Override
    public Task create(Task task, int days, int hours, int minutes) {
        updateRepo();
        task.init(days, hours, minutes);
        task.setStatus(Status.IN_PROGRESS);
        return taskRepository.save(task);
    }

    @Override
    public Task update(long id, Task task, int days, int hours, int minutes){
        updateRepo();
        Task updatedTask = taskRepository
                .findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, ERROR + id));

        if (days != 0 || hours != 0 || minutes != 0) {
            updatedTask.setDateOfDeadline(
                    updatedTask.getDateOfCreated()
                            .plusDays(days)
                            .plusHours(hours)
                            .plusMinutes(minutes)
            );
        }
        updatedTask.setName(task.getName());
        updatedTask.setDescription(task.getDescription());
        return taskRepository.save(updatedTask);
    }

    @Override
    public Task getById(long id) {
        updateRepo();
        return taskRepository
                .findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, ERROR + id));
    }

    @Override
    public List<Task> getAll() {
        updateRepo();
        return taskRepository.findAllByOrderByIdDesc();
    }
}
