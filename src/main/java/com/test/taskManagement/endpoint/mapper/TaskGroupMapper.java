package com.test.taskManagement.endpoint.mapper;


import com.test.taskManagement.endpoint.dto.TaskDto;
import com.test.taskManagement.endpoint.dto.TaskGroupDto;
import com.test.taskManagement.entity.Task;
import com.test.taskManagement.entity.TaskGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskGroupMapper {
    private final TaskMapper taskMapper;

    @Autowired
    public TaskGroupMapper(TaskMapper taskMapper){
        this.taskMapper = taskMapper;
    }

    public TaskGroupDto entityToDto(TaskGroup taskGroup){
        return new TaskGroupDto(taskGroup.getId(), taskGroup.getName(),taskGroup.getDescription(), taskMapper.TaskstoTaskDtos(taskGroup.getTasks()));
    }

    public List<TaskGroupDto> entitiesToDtos(List<TaskGroup> taskgroups){
        if (taskgroups == null){
            return null;
        }
        List<TaskGroupDto> dtos = new ArrayList<>();
        for(TaskGroup taskGroup : taskgroups){
            dtos.add(entityToDto(taskGroup));
        }
        return dtos;
    }
}
