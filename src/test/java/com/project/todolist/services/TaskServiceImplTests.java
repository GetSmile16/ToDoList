package com.project.todolist.services;

import com.project.todolist.domains.Task;
import com.project.todolist.domains.enums.Status;
import com.project.todolist.repositories.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    static void init() {

    }

    @AfterEach void clear() {
        taskRepository.deleteAll();
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

//Do fixed clock
//    @Test
//    void create() {
//        Task actual = taskService.create(task, DAYS, HOURS, MINUTES);
//
//        Task expected = Task.builder()
//                .id(actual.getId())
//                .name("Example")
//                .description("Task for example")
//                .status(Status.IN_PROGRESS)
//                .dateOfCreated(LocalDateTime.MIN.truncatedTo(ChronoUnit.MINUTES))
//                .dateOfDeadline(LocalDateTime.MIN.truncatedTo(ChronoUnit.MINUTES)
//                        .plusDays(DAYS)
//                        .plusHours(HOURS)
//                        .plusMinutes(MINUTES))
//                .build();
//
//        Assertions.assertEquals(expected, actual);
//    }

    @Test
    void updateWithoutException() {
        long id = taskRepository.save(Task.builder()
                .name("Example")
                .description("Task for example")
                .status(Status.IN_PROGRESS)
                .dateOfCreated(now)
                .dateOfDeadline(now.plusDays(1))
                .build()
        ).getId();

        Task actual = taskService.update(
                id,
                Task.builder()
                        .name("Example")
                        .description("Task for example | changed")
                        .status(Status.IN_PROGRESS)
                        .dateOfCreated(now)
                        .dateOfDeadline(now)
                        .build(),
                1,
                0,
                1
        );

        Task expected = Task.builder()
                .id(id)
                .name("Example")
                .description("Task for example | changed")
                .status(Status.IN_PROGRESS)
                .dateOfCreated(now)
                .dateOfDeadline(now.plusDays(1).plusMinutes(1))
                .build();

        Assertions.assertEquals(expected, actual);
    }

    @Test()
    void exceptionTest() {
        Task task = new Task();
        Exception exception = Assertions.assertThrows(
                HttpClientErrorException.class,
                () -> taskService.update(999, task, 0, 0, 0)
        );

        String expected = "404 Task not found with id 999";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }
}
