package com.test.taskManagement.unittest;

import com.test.taskManagement.Exception.ValidationException;
import com.test.taskManagement.entity.Task;
import com.test.taskManagement.entity.TaskStatus;
import com.test.taskManagement.service.TaskService;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TaskServiceTest {
    @Autowired
    TaskService taskService;

    @Autowired
    PlatformTransactionManager txm;
    TransactionStatus txstatus;

    Task testTask1;
    Task testTask2;
    Task testTask3;

    Task updateTask1;

    @BeforeEach
    public void setupDBTransaction() {
        testTask1 = new Task(null,"Task 1","Add Mushrooms to Cauldron", TaskStatus.DOING,null,0L,null,null,null);
        testTask2 = new Task(null,"Task 2","Add Mushrooms to Cauldron", TaskStatus.DOING,null,0L,null,null,null);
        testTask3 = new Task(null,"Task 3","Add Mushrooms to Cauldron", TaskStatus.DOING,null,0L,null,null,null);
        updateTask1 = new Task(1L,"Task 1 Updated","Add Mushrooms to Cauldron Updated", TaskStatus.DONE,null,0L,null,null,null);


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
    @DisplayName("Creating non invalid task throws ValidationException")
    public void creatingTask_inValid_shouldThrowValidationException() {
        testTask1.setName("INVALID LENGTH AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        assertThrows(ValidationException.class,
                () -> taskService.createTask(testTask1));
    }

    @Test
    @DisplayName("Updating non invalid task throws ValidationException")
    public void updatingTask_inValid_shouldThrowValidationException() {
        testTask1.setName("INVALID LENGTH AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        assertThrows(ValidationException.class,
                () -> taskService.updateTask(testTask1));
    }

    @Test
    @DisplayName("Updating non invalid task throws ValidationException")
    public void gettingTask_ValidTask_shouldAssembleSubTasks() {
        Task task1 = taskService.createTask(testTask1);
        testTask2.setIs_subtask_of_task(task1.getId());
        Task task2 = taskService.createTask(testTask2);
        testTask3.setIs_subtask_of_task(task2.getId());
        Task task3 = taskService.createTask(testTask3);

        Task actualTask = taskService.getTaskById(task1.getId());

        assertTrue(actualTask.getSubTasks().size()==1);
        assertTrue(actualTask.getSubTasks().get(0).getName().equals(task2.getName()));
        assertTrue(actualTask.getSubTasks().get(0).getSubTasks().size()==1);
        assertTrue(actualTask.getSubTasks().get(0).getSubTasks().get(0).getName().equals(task3.getName()));

    }


}
