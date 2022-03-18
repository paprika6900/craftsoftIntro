CREATE TABLE IF NOT EXISTS task
(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
description VARCHAR(500),
status ENUM('TODO','DOING','DONE'),
assignee BIGINT,
time_minutes DECIMAL,
is_subtask_of_task BIGINT,
is_subtask_of_group BIGINT
);

CREATE TABLE IF NOT EXISTS task_group
(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
description VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS employee
(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL
);

ALTER TABLE task
  ADD FOREIGN KEY(assignee)
  REFERENCES employee(id);

ALTER TABLE task
  ADD FOREIGN KEY(is_subtask_of_group)
  REFERENCES task_group(id);

ALTER TABLE task
  ADD FOREIGN KEY(is_subtask_of_task)
  REFERENCES task(id);