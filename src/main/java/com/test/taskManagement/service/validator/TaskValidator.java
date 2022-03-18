package com.test.taskManagement.service.validator;

import com.test.taskManagement.Exception.ValidationException;
import com.test.taskManagement.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskValidator {
    public void Validate(Task task) throws ValidationException {
        if(task.getName().length()>250){
            throw new ValidationException("Name too Long");
        }
        if(task.getDescription().length()>500){
            throw new ValidationException("Description too Long");
        }
    }
}
