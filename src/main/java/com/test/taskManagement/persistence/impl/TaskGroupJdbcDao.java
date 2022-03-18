package com.test.taskManagement.persistence.impl;

import com.test.taskManagement.entity.Task;
import com.test.taskManagement.entity.TaskGroup;
import com.test.taskManagement.entity.TaskStatus;
import com.test.taskManagement.persistence.TaskGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskGroupJdbcDao implements TaskGroupDao {
    private static final String TABLE_NAME = "task_group";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskGroupJdbcDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TaskGroup> getAllTaskGroups() {

        final String sql = "SELECT * FROM " + TABLE_NAME;
        List<TaskGroup> taskGroups = jdbcTemplate.query(sql, this::mapRow);
        return taskGroups;
    }

    @Override
    public TaskGroup getTaskGroupById(Long id) {
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";
        List<TaskGroup> taskGroups = jdbcTemplate.query(sql, this::mapRow,id);
        //exception handling
        return taskGroups.get(0);
    }

    private TaskGroup mapRow(ResultSet resultSet, int i) throws SQLException {
        final TaskGroup taskGroup = new TaskGroup(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                null
        );
        return taskGroup;
    }
}
