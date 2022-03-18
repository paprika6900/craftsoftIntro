package com.test.taskManagement.persistence;

import com.test.taskManagement.entity.Task;
import com.test.taskManagement.entity.TaskGroup;

import java.util.ArrayList;
import java.util.List;

public interface TaskDao {

    /**
     *
     * @return all Tasks in DB or Empty
     */
    List<Task> getAllTasks();

    /**
     * @param id of task to return
     * @return list of direct subtasks
     * @throws NotFoundException if id does not exist
     */
    List<Task> getAllSubTasksByParentTaskId(Long id);

    /**
     * @param id of task to return
     * @return list of direct subtasks
     * @throws NotFoundException if id does not exist
     */
    List<Task> getAllSubTasksByParentGroupId(Long id);


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
     */
    Task createTask(Task task);

    /**
     * @param id of task to delete
     * @return true if deleted
     * @throws NotFoundException if id doesn't exist
     */
    boolean deleteTask(Long id);

    /**
     * @param task a full valid task
     * @return updated task
     * @throws ValidationException if parameter task is invalid
     */
    Task updateTask(Task task);
}
