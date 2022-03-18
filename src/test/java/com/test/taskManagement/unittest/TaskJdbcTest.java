package com.test.taskManagement.unittest;


import com.test.taskManagement.Exception.NotFoundException;
import com.test.taskManagement.entity.Task;
import com.test.taskManagement.entity.TaskStatus;
import com.test.taskManagement.persistence.TaskDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TaskJdbcTest {
    @Autowired
    PlatformTransactionManager txm;

    TransactionStatus txstatus;

    @Autowired
    TaskDao taskDao;

    Task testTask1;
    Task testTask2;
    Task testTask3;
    Task updateTask1;

    @BeforeEach
    public void setupDBTransaction() {
        testTask1 = new Task(null,"Task 1","Add Mushrooms to Cauldron", TaskStatus.DOING,null,0L,null,null,null);
        testTask2 = new Task(null,"Task 2","Add Mushrooms to Cauldron", TaskStatus.DOING,null,0L,null,null,null);
        testTask3 = new Task(null,"Task 3","Add Mushrooms to Cauldron", TaskStatus.DOING,null,0L,null,null,null);
        updateTask1 = new Task(1L,"Add Mushrooms Updated","Add Mushrooms to Cauldron Updated", TaskStatus.DONE,null,0L,null,null,null);


        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        txstatus = txm.getTransaction(def);
        assumeTrue(txstatus.isNewTransaction());
        txstatus.setRollbackOnly();
    }

    @AfterEach
    public void tearDownDBData() {
        txm.rollback(txstatus);
    }

    @Test
    @DisplayName("Finding non existent task throws NotFoundException")
    public void findingTaskById_nonExisting_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class,
                () -> taskDao.getTaskById(-1L));
    }

    @Test
    @DisplayName("Finding existent task returns task")
    public void findingTaskById_Existing_shouldReturnTask() {
        Task createdTask = taskDao.createTask(testTask1);
        Long id = createdTask.getId();

        assertEquals(testTask1.getName(),taskDao.getTaskById(id).getName());
        assertEquals(testTask1.getDescription(),taskDao.getTaskById(id).getDescription());
        assertEquals(testTask1.getStatus(),taskDao.getTaskById(id).getStatus());
        assertEquals(testTask1.getEmployeeId(),taskDao.getTaskById(id).getEmployeeId());
        assertEquals(testTask1.getTimeMinutes(),taskDao.getTaskById(id).getTimeMinutes());
    }

    @Test
    @DisplayName("Updating a task should find updated task")
    public void updatingTask_Existing_shouldFindUpdatedTask() {
        Task createdTask = taskDao.createTask(testTask1);
        Long id = createdTask.getId();
        updateTask1.setId(id);
        taskDao.updateTask(updateTask1);

        assertEquals(updateTask1.getName(),taskDao.getTaskById(id).getName());
        assertEquals(updateTask1.getDescription(),taskDao.getTaskById(id).getDescription());
        assertEquals(updateTask1.getStatus(),taskDao.getTaskById(id).getStatus());
        assertEquals(updateTask1.getEmployeeId(),taskDao.getTaskById(id).getEmployeeId());
        assertEquals(updateTask1.getTimeMinutes(),taskDao.getTaskById(id).getTimeMinutes());
    }

    @Test
    @DisplayName("Deleting a task returns true and task cannot be found anymore")
    public void deletingTask_Existing_shouldDeleteTask() {
        Task createdTask = taskDao.createTask(testTask1);
        Long id = createdTask.getId();
        assertTrue(taskDao.deleteTask(id));
        assertThrows(NotFoundException.class,
                () -> taskDao.getTaskById(id));
    }

    @Test
    @DisplayName("Deleting non existent task returns False")
    public void deletingTaskById_nonExisting_shouldReturnFalse() {
        assertFalse(taskDao.deleteTask(-1L));
    }

    @Test
    @DisplayName("Getting ALl retrieves All")
    public void gettingAllTasks_ValidTask_shouldRetrieveAllTasks() {
        taskDao.createTask(testTask1);
        taskDao.createTask(testTask2);
        taskDao.createTask(testTask3);
        assertEquals(3,taskDao.getAllTasks().size());

    }

}
