package com.test.taskManagement.endpoint;

import com.test.taskManagement.Exception.NotFoundException;
import com.test.taskManagement.Exception.PersistenceException;
import com.test.taskManagement.Exception.ValidationException;
import com.test.taskManagement.endpoint.dto.TaskGroupDto;
import com.test.taskManagement.endpoint.mapper.TaskGroupMapper;
import com.test.taskManagement.service.TaskGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(TaskGroupEndpoint.BASE_URL)
public class TaskGroupEndpoint {
    static final String BASE_URL = "/taskgroup";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TaskGroupMapper taskGroupMapper;
    private final TaskGroupService taskGroupService;

    @Autowired
    public TaskGroupEndpoint(TaskGroupMapper taskGroupMapper, TaskGroupService taskGroupService){
        this.taskGroupMapper = taskGroupMapper;
        this.taskGroupService = taskGroupService;
    }


    @GetMapping(value = "/")
    public List<TaskGroupDto> getAll() {
        LOGGER.info("GET " + BASE_URL + "/");
        try{
            return taskGroupMapper.entitiesToDtos(taskGroupService.getAllTaskGroups());
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch(PersistenceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch(ValidationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public TaskGroupDto getTaskGroupById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}",id);
        try{
            return taskGroupMapper.entityToDto(taskGroupService.getTaskGroupById(id));
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch(PersistenceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch(ValidationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }
}
