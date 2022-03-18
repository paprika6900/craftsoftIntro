package com.test.taskManagement.service;

import com.test.taskManagement.entity.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskService {
    /**
     *
     * @return all Tasks in DB or Empty subtasks contain duplicates
     */
    List<Task> getAllTasks();

    /**
     * @param id of task to return
     * @return one Task
     * @throws NotFoundException if id does not exist
     */
    Task getTaskById(Long id);

    /**
     * @param task a complete task
     * @return the created task with id
     * @throws ValidationException if parameter task is invalid
     * @throws PersistenceException if db goes wrong
     */
    Task createTask(Task task);

    /**
     * @param id of task to delete
     * @return true if deleted
     * @throws NotFoundException if id doesn't exist
     * @throws PersistenceException if db goes wrong
     */
    boolean deleteTask(Long id);

    /**
     * @param task a full valid task
     * @return updated task
     * @throws ValidationException if parameter task is invalid
     * @throws NotFoundException if parameter task is invalid
     * @throws PersistenceException if db goes wrong
     */
    Task updateTask(Task task);

    /**
     * @param id of task to return
     * @return list of direct subtasks
     */
    List<Task> getAllSubTasksByParentGroupId(Long id);
}
