package com.test.taskManagement.endpoint.mapper;

import com.test.taskManagement.endpoint.dto.TaskDto;
import com.test.taskManagement.entity.Employee;
import com.test.taskManagement.entity.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapper {

    public TaskDto entityToDto(Task task){
        return new TaskDto(task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                task.getEmployeeId(),
                task.getTimeMinutes(),
                TaskstoTaskDtos(task.getSubTasks()),
                task.getIs_subtask_of_task(),
                task.getIs_subtask_of_group());
    }

    public Task dtoToEntity(TaskDto taskDto){
        return new Task(taskDto.getId(),
                taskDto.getName(),
                taskDto.getDescription(),
                taskDto.getStatus(),
                taskDto.getEmployeeId(),
                taskDto.getTimeMinutes(),
                TaskDtostoTasks(taskDto.getSubTasks()),
                taskDto.getIs_subtask_of_task(),
                taskDto.getIs_subtask_of_group());
    }


    public List<TaskDto> TaskstoTaskDtos(List<Task> tasks){
        if (tasks == null){
            return null;
        }
        List<TaskDto> dtos = new ArrayList<>();
        for(Task task : tasks){
            dtos.add(entityToDto(task));
        }
        return dtos;
    }

    public List<Task> TaskDtostoTasks(List<TaskDto> taskDtos){
        if (taskDtos == null){
            return null;
        }
        List<Task> tasks = new ArrayList<>();
        for(TaskDto taskDto : taskDtos){
            tasks.add(dtoToEntity(taskDto));
        }
        return tasks;
    }
}
