package com.test.taskManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Task {
    private Long id;
    private String name;
    private String description;
    private TaskStatus status;
    private Long employeeId;
    private Long timeMinutes;
    private List<Task> subTasks;
    private Long is_subtask_of_task;
    private Long is_subtask_of_group;
}
