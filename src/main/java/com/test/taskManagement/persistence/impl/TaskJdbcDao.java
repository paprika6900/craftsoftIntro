package com.test.taskManagement.persistence.impl;

import com.test.taskManagement.Exception.NotFoundException;
import com.test.taskManagement.Exception.PersistenceException;
import com.test.taskManagement.entity.Employee;
import com.test.taskManagement.entity.Task;
import com.test.taskManagement.entity.TaskGroup;
import com.test.taskManagement.entity.TaskStatus;
import com.test.taskManagement.persistence.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskJdbcDao implements TaskDao {

    private static final String TABLE_NAME = "task";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Task> getAllTasks() {
        final String sql = "SELECT * FROM " + TABLE_NAME;
        List<Task> tasks = jdbcTemplate.query(sql, this::mapRow);
        return tasks;
    }

    @Override
    public List<Task> getAllSubTasksByParentTaskId(Long id) {
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE is_subtask_of_task = ?;";
        List<Task> tasks = jdbcTemplate.query(sql, this::mapRow,id);
        return tasks;
    }

    @Override
    public List<Task> getAllSubTasksByParentGroupId(Long id) {
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE is_subtask_of_group = ?;";
        List<Task> tasks = jdbcTemplate.query(sql, this::mapRow,id);
        return tasks;
    }

    @Override
    public Task getTaskById(Long id) {
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";
        List<Task> tasks = jdbcTemplate.query(sql, this::mapRow,id);
        if(tasks == null || tasks.isEmpty()){
            throw new NotFoundException("Could not find Task with ID");
        }
        return tasks.get(0);
    }

    @Override
    public Task createTask(Task task) {
        final String sql = "INSERT INTO " + TABLE_NAME + "(NAME, DESCRIPTION, STATUS, ASSIGNEE, TIME_MINUTES, IS_SUBTASK_OF_TASK, IS_SUBTASK_OF_GROUP) VALUES (?,?,?,?,?,?,?)";

        try{
            //jdbcTemplate.update(sql,task.getName(),task.getDescription(),task.getStatus().toString(),task.getEmployeeId(),task.getTimeMinutes(),task.getIs_subtask_of_task(),task.getIs_subtask_of_group());

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, task.getName());
                ps.setString(2, task.getDescription());
                ps.setString(3, task.getStatus().toString());
                ps.setObject(4, task.getEmployeeId());
                ps.setObject(5, task.getTimeMinutes());
                ps.setObject(6, task.getIs_subtask_of_task());
                ps.setObject(7, task.getIs_subtask_of_group());
                return ps;
            }, keyHolder);
            task.setId(keyHolder.getKey().longValue());
            return task;
        } catch(Exception e){
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public boolean deleteTask(Long id) {
        boolean success = false;
        final String sql = "Delete FROM " + TABLE_NAME + " WHERE Id = ?";
        try {
            success = jdbcTemplate.update(sql, id) == 1;
        } catch(Exception e){
            throw new PersistenceException(e.getMessage());
        }
        return success;
    }

    @Override
    public Task updateTask(Task task) {
        final String sql = "Update " + TABLE_NAME + " SET NAME = ?, DESCRIPTION = ?, STATUS = ?, ASSIGNEE = ?, TIME_MINUTES = ?, IS_SUBTASK_OF_TASK = ?, IS_SUBTASK_OF_GROUP =? WHERE Id = ?";
        try{
            jdbcTemplate.update(sql,task.getName(),task.getDescription(),task.getStatus().toString(),task.getEmployeeId(),task.getTimeMinutes(),task.getIs_subtask_of_task(),task.getIs_subtask_of_group(), task.getId());
        } catch(Exception e){
            throw new PersistenceException(e.getMessage());
        }
        return task;
    }


    private Task mapRow(ResultSet resultSet, int i) throws SQLException {
        final Task task = new Task(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("status").equals("TODO") ? TaskStatus.TODO : (resultSet.getString("status").equals("DOING") ? TaskStatus.DOING : TaskStatus.DONE),
                resultSet.getLong("assignee")==0L ? null : resultSet.getLong("assignee"),
                resultSet.getLong("time_minutes"),
                null,
                resultSet.getLong("is_subtask_of_task"),
                resultSet.getLong("is_subtask_of_group")
        );
        return task;
    }
}
