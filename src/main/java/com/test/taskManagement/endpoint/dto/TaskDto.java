package com.test.taskManagement.endpoint.dto;

import com.test.taskManagement.entity.Employee;
import com.test.taskManagement.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private TaskStatus status;
    private Long employeeId;
    private Long timeMinutes;
    private List<TaskDto> subTasks;
    private Long is_subtask_of_task;
    private Long is_subtask_of_group;
}
