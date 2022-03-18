package com.test.taskManagement.service;

import com.test.taskManagement.entity.Task;
import com.test.taskManagement.entity.TaskGroup;

import java.util.ArrayList;
import java.util.List;

public interface TaskGroupService {

    /**
     *
     * @return all TaskGroups in DB or Empty
     */
    List<TaskGroup> getAllTaskGroups();

    /**
     * @param id of taskgroup to return
     * @return one Taskgroup
     * @throws NotFoundException if id does not exist
     */
    TaskGroup getTaskGroupById(Long id);
}
