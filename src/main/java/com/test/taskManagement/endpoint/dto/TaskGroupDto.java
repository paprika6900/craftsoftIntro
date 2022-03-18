package com.test.taskManagement.endpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TaskGroupDto {
    private Long id;
    private String name;
    private String description;
    private List<TaskDto> tasks;
}
