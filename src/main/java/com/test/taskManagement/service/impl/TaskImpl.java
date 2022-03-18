package com.test.taskManagement.service.impl;

import com.test.taskManagement.entity.Task;
import com.test.taskManagement.persistence.TaskDao;
import com.test.taskManagement.service.TaskService;
import com.test.taskManagement.service.validator.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskImpl implements TaskService {

    private final TaskDao taskDao;
    private final TaskValidator taskValidator;

    @Autowired
    public TaskImpl(TaskDao taskDao, TaskValidator taskValidator){
        this.taskDao = taskDao;
        this.taskValidator = taskValidator;
    }

    @Override
    @Transactional
    public List<Task> getAllTasks() {

        return assembleTasksRec(taskDao.getAllTasks());
    }

    @Transactional
    public List<Task> assembleTasksRec(List<Task> tasks){
        if(tasks == null ||tasks.isEmpty()){
            return null;
        }
        for(Task task : tasks){
            List<Task> subTasks = taskDao.getAllSubTasksByParentTaskId(task.getId());
            task.setSubTasks(assembleTasksRec(subTasks));
        }
        return tasks;
    }

    @Override
    @Transactional
    public Task getTaskById(Long id) {
        Task fetchedTask = taskDao.getTaskById(id);
        fetchedTask.setSubTasks(assembleTasksRec(taskDao.getAllSubTasksByParentTaskId(id)));
        return fetchedTask;
    }

    @Override
    @Transactional
    public Task createTask(Task task) {
        taskValidator.Validate(task);
        return taskDao.createTask(task);
    }

    @Override
    @Transactional
    public boolean deleteTask(Long id) {
        return taskDao.deleteTask(id);
    }

    @Override
    @Transactional
    public Task updateTask(Task task) {
        taskValidator.Validate(task);
        return taskDao.updateTask(task);
    }

    @Override
    public List<Task> getAllSubTasksByParentGroupId(Long id) {
        return assembleTasksRec(taskDao.getAllSubTasksByParentGroupId(id));
    }
}
