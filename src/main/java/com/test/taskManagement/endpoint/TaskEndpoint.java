package com.test.taskManagement.endpoint;

import com.test.taskManagement.Exception.NotFoundException;
import com.test.taskManagement.Exception.PersistenceException;
import com.test.taskManagement.Exception.ValidationException;
import com.test.taskManagement.endpoint.dto.TaskDto;
import com.test.taskManagement.endpoint.mapper.TaskMapper;
import com.test.taskManagement.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(TaskEndpoint.BASE_URL)
public class TaskEndpoint {
    static final String BASE_URL = "/task";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TaskMapper taskMapper;
    private final TaskService taskService;



    @Autowired
    public TaskEndpoint(TaskMapper taskmapper, TaskService taskService){
        this.taskMapper = taskmapper;
        this.taskService = taskService;
    }


    @GetMapping(value = "/")
    public List<TaskDto> getAll() {
        LOGGER.info("GET " + BASE_URL + "/");
        try{
            return taskMapper.TaskstoTaskDtos(taskService.getAllTasks());
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch(PersistenceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch(ValidationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public TaskDto getTaskById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}",id);
        try{
            return taskMapper.entityToDto(taskService.getTaskById(id));
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch(PersistenceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch(ValidationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(value ="/{id}")
    public boolean deleteTask(@PathVariable("id") Long id){
        LOGGER.info("DELETE " + BASE_URL + "/{}",id);
        try{
            return taskService.deleteTask(id);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch(PersistenceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch(ValidationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(value="/")
    public TaskDto createTask(@RequestBody TaskDto task){
        LOGGER.info("POST " + BASE_URL + "/{}",task.getStatus());
        try{
            return taskMapper.entityToDto(taskService.createTask(taskMapper.dtoToEntity(task)));
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch(PersistenceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch(ValidationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(value="/")
    public TaskDto updateTask(@RequestBody TaskDto task){
        LOGGER.info("PUT " + BASE_URL + "/");
        try{
            return taskMapper.entityToDto(taskService.updateTask(taskMapper.dtoToEntity(task)));
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch(PersistenceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch(ValidationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
