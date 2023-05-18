package com.arroganteIT.rest.persistance.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.experimental.Accessors;

import java.time.LocalDate;

@Entity
@Data
@Accessors(chain = true)
@Table(indexes = @Index(name = "task_key_idx", columnList = "taskKey"))
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false)
    @NotBlank
    private String department;

    private String taskKey;

    private LocalDate createdDate;

    private LocalDate dueDate;

    @NotBlank
    private String taskName;

    @NotBlank
    private String description_task;

    @JsonIgnore
    @JoinColumn(name = "employee_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Employee employee;

    @PostPersist
    public void generateTaskKey() {
        if (taskKey == null) {
            taskKey = StringUtils.joinWith("-", department, taskId);
        }
    }
}


































