package com.test.taskManagement.service.impl;

import com.test.taskManagement.entity.TaskGroup;
import com.test.taskManagement.persistence.TaskGroupDao;
import com.test.taskManagement.service.TaskGroupService;
import com.test.taskManagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskGroupImpl implements TaskGroupService {
    private final TaskGroupDao taskGroupDao;
    private final TaskService taskService;

    @Autowired
    public TaskGroupImpl(TaskGroupDao taskGroupDao, TaskService taskService){
        this.taskGroupDao = taskGroupDao;
        this.taskService = taskService;
    }

    @Override
    public List<TaskGroup> getAllTaskGroups() {
        List<TaskGroup> taskGroups = taskGroupDao.getAllTaskGroups();
        for(TaskGroup taskGroup : taskGroups){
            taskGroup.setTasks(taskService.getAllSubTasksByParentGroupId(taskGroup.getId()));
        }
        return taskGroups;
    }

    @Override
    public TaskGroup getTaskGroupById(Long id) {
        TaskGroup taskGroup = taskGroupDao.getTaskGroupById(id);
        taskGroup.setTasks(taskService.getAllSubTasksByParentGroupId(id));
        return taskGroup;
    }

}
