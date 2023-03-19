package com.project.todolist.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.todolist.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
@SuperBuilder
public class Task extends TaskTemplate {
    private static final int MAX_SYMBOLS = 300;
    @Size(max = MAX_SYMBOLS)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfDeadline;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfCreated;
    public void init() {
        dateOfCreated = LocalDateTime.now();
    }
}
