package com.project.todolist.service;

import com.project.todolist.entity.DayTask;
import com.project.todolist.repository.DayTaskRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class DayTaskServiceImpl implements DayTaskService {
    private DayTaskRepository taskRepository;
    @Override
    public DayTask getRandom() {
        Iterable<DayTask> tasks = taskRepository.findAll();

        List<DayTask> taskList = new ArrayList<>();
        for (DayTask task : tasks) {
            taskList.add(task);
        }

        Random random = new Random();

        return taskList.get(random.nextInt(taskList.size()));
    }

    @PostConstruct
    public void init() throws IOException {
        File file = ResourceUtils.getFile("classpath:data/tasks.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                taskRepository.save(new DayTask(line));
            }
        }
    }
}
