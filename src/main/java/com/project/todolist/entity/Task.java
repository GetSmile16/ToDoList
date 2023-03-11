package com.project.todolist.entity;

import com.project.todolist.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    private LocalDateTime dateOfDeadline;
    private LocalDateTime dateOfCreated;

    public void init() {
        dateOfCreated = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }
}
