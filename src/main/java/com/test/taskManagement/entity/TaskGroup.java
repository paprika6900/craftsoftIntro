package com.test.taskManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TaskGroup {
    private Long id;
    private String name;
    private String description;
    private List<Task> tasks;

}
