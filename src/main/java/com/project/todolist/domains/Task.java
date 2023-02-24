package com.project.todolist.domains;

import com.project.todolist.domains.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @NotBlank(message = "Название не должно быть пустым")
    private String name;
    @Size(max = 300)
    @Column(nullable = false)
    @NotBlank(message = "Описание не должно быть пустым")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    private LocalDateTime dateOfDeadline;
    private LocalDateTime dateOfCreated;

    public void init(int days, int hours, int minutes) {
        dateOfCreated = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        dateOfDeadline = dateOfCreated
                .plusDays(days)
                .plusHours(hours)
                .plusMinutes(minutes);
    }
}
