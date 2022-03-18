package com.test.taskManagement.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.test.taskManagement.Exception.NotFoundException;
import com.test.taskManagement.Exception.ValidationException;
import com.test.taskManagement.endpoint.dto.TaskDto;
import com.test.taskManagement.entity.Task;
import com.test.taskManagement.entity.TaskStatus;
import com.test.taskManagement.persistence.TaskDao;
import org.hibernate.annotations.NotFound;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TaskEnpointTest {
    @Autowired
    PlatformTransactionManager txm;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    TaskDao taskDao;


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
    @DisplayName("Finding existent task returns task")
    public void findingTaskById_Existing_shouldReturnTask() throws Exception{
        taskDao.createTask(testTask1);
        taskDao.createTask(testTask2);
        MvcResult result = mockMvc.perform(get("/task/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        List<TaskDto> dto = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
        assertEquals(testTask1.getName(),dto.get(0).getName());
        assertEquals(testTask2.getName(),dto.get(1).getName());
    }

    @Test
    @DisplayName("Finding non existant task returns Not Found")
    public void findingTaskById_nonExisting_shouldReturnTask() throws Exception{
        MvcResult result = mockMvc.perform(get("/task/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());

    }

    @Test
    @DisplayName("Creating Task returns Task with DB ID")
    public void createTask_valid_shouldReturnTaskWithId() throws Exception{
        String body = objectMapper.writeValueAsString(testTask1);
        MvcResult result = mockMvc.perform(post("/task/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(body))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        TaskDto dto = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
        assertEquals(testTask1.getName(),dto.getName());
        assertNotNull(dto.getId());
    }

    @Test
    @DisplayName("Deleting existent task returns true")
    public void deletingTaskById_Existing_shouldDeleteTask() throws Exception{
        Task task = taskDao.createTask(testTask1);
        MvcResult result = mockMvc.perform(delete("/task/"+task.getId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertThrows(NotFoundException.class,
                () -> taskDao.getTaskById(task.getId()));
    }

    @Test
    @DisplayName("Updating existent task returns task")
    public void updatingTaskById_Existing_shouldReturnTask() throws Exception{
        Task task = taskDao.createTask(testTask1);
        assertEquals(testTask1.getName(),taskDao.getTaskById(task.getId()).getName());
        updateTask1.setId(task.getId());
        String body = objectMapper.writeValueAsString(updateTask1);
        MvcResult result = mockMvc.perform(put("/task/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(body))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        TaskDto dto = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
        assertEquals(updateTask1.getName(),dto.getName());
        assertNotNull(dto.getId());
        assertEquals(updateTask1.getName(),taskDao.getTaskById(task.getId()).getName());
    }
}
