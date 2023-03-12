package com.project.todolist.service;

import com.project.todolist.dto.TaskDto;
import com.project.todolist.entity.Task;
import com.project.todolist.entity.enums.Status;
import com.project.todolist.model.DeadlineDateTime;
import com.project.todolist.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
class TaskServiceImplTests {
    @Autowired
    private TaskServiceImpl taskService;
    @Autowired
    private TaskRepository taskRepository;

    private static final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

    @AfterEach
    void clear() {
        taskRepository.deleteAll();
    }

    @Test
    void delete() {
        Exception exception = Assertions.assertThrows(
                HttpClientErrorException.class,
                () -> taskService.delete(999)
        );

        String expected = "204 Task not found with id 999";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);

        exception = Assertions.assertThrows(
                HttpClientErrorException.class,
                () -> taskService.delete(999)
        );
        String expectedEx = "204 Task not found with id 999";
        String actualEx = exception.getMessage();
        Assertions.assertEquals(expectedEx, actualEx);
    }

    @Test
    void updateRepo() {
        List<Task> expected = new ArrayList<>();
        expected.add(Task.builder()
                .name("Example")
                .description("Task for example1")
                .status(Status.IN_PROGRESS)
                .dateOfCreated(now)
                .dateOfDeadline(now.plusDays(10))
                .build()
        );

        expected.add(Task.builder()
                .name("Example")
                .description("Task for example2")
                .status(Status.IN_PROGRESS)
                .dateOfCreated(now.minusDays(3))
                .dateOfDeadline(now.minusDays(3).plusDays(1))
                .build()
        );


        expected.add(Task.builder()
                .name("Example")
                .description("Task for example3")
                .status(Status.FAILED)
                .dateOfCreated(now.minusDays(2))
                .dateOfDeadline(now.minusDays(2).plusDays(1))
                .build()
        );

        expected.add(Task.builder()
                .name("Example")
                .description("Task for example4")
                .status(Status.RESOLVED)
                .dateOfCreated(now)
                .dateOfDeadline(now.plusDays(5))
                .build()
        );

        taskRepository.saveAll(expected);

        taskService.updateRepo();

        expected.get(1).setStatus(Status.FAILED);

        List<Task> actual = taskRepository.findAllByOrderByIdDesc();
        actual.sort(Comparator.comparingLong(Task::getId));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create() {
        LocalDateTime next = now.plusDays(1).plusMinutes(1);

        Task actual = taskService.create(new TaskDto(
                Task.builder()
                        .name("Example")
                        .description("Task for example")
                        .dateOfCreated(now)
                        .build(),
                DeadlineDateTime.builder()
                        .day(next.getDayOfMonth())
                        .month(next.getMonthValue())
                        .year(next.getYear())
                        .hours(next.getHour())
                        .minutes(next.getMinute())
                        .build())
        );

        Task expected = Task.builder()
                .id(actual.getId())
                .name("Example")
                .description("Task for example")
                .status(Status.IN_PROGRESS)
                .dateOfCreated(now)
                .dateOfDeadline(next)
                .build();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() {
        LocalDateTime next = now.plusDays(1).plusMinutes(1);

        long id = taskRepository.save(Task.builder()
                .name("Example")
                .description("Task for example")
                .status(Status.IN_PROGRESS)
                .dateOfCreated(now)
                .dateOfDeadline(now.plusHours(1))
                .build()
        ).getId();

        TaskDto example = new TaskDto(
                Task.builder()
                        .name("Example")
                        .description("Task for example | changed")
                        .dateOfCreated(now)
                        .build(),
                DeadlineDateTime.builder()
                        .day(next.getDayOfMonth())
                        .month(next.getMonthValue())
                        .year(next.getYear())
                        .hours(next.getHour())
                        .minutes(next.getMinute())
                        .build()
        );

        Task actual = taskService.update(id, example);

        Task expected = Task.builder()
                .id(id)
                .name("Example")
                .description("Task for example | changed")
                .status(Status.IN_PROGRESS)
                .dateOfCreated(now)
                .dateOfDeadline(next)
                .build();

        Assertions.assertEquals(expected, actual);

        Exception exception = Assertions.assertThrows(
                HttpClientErrorException.class,
                () -> taskService.update(999, new TaskDto())
        );
        String expectedEx = "404 Task not found with id 999";
        String actualEx = exception.getMessage();
        Assertions.assertEquals(expectedEx, actualEx);

        long idEx = taskRepository.save(Task.builder()
                .name("Example")
                .description("Task for example")
                .status(Status.RESOLVED)
                .dateOfCreated(now)
                .dateOfDeadline(now)
                .build()
        ).getId();

        exception = Assertions.assertThrows(
                HttpClientErrorException.class,
                () -> taskService.update(idEx, example)
        );
        expectedEx = "200 Task with id " + idEx + " is resolved";
        actualEx = exception.getMessage();
        Assertions.assertEquals(expectedEx, actualEx);
    }


    @Test
    void resolve() {
        Task task = taskRepository.save(
                Task.builder()
                        .name("Example")
                        .description("Task for example")
                        .status(Status.IN_PROGRESS)
                        .dateOfCreated(now)
                        .dateOfDeadline(now.plusDays(10))
                        .build()
        );

        taskService.resolve(task.getId());

        Task expected = Task.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .status(Status.RESOLVED)
                .dateOfCreated(task.getDateOfCreated())
                .dateOfDeadline(task.getDateOfDeadline())
                .build();

        Task actual = taskRepository.findById(task.getId())
                .orElse(new Task());

        Assertions.assertEquals(expected, actual);

        Exception exception = Assertions.assertThrows(
                HttpClientErrorException.class,
                () -> taskService.resolve(task.getId())
        );

        String expectedEx = "200 Task with id " + task.getId() + " is resolved";
        String actualEx = exception.getMessage();

        Assertions.assertEquals(expectedEx, actualEx);
    }
}
