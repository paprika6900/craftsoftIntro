-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data (in this assignments context)
DELETE FROM task where ID <0;
DELETE FROM task_group where ID < 0;
DELETE FROM employee where ID <0;

INSERT INTO employee (ID, NAME)
VALUES (-1, 'Fred'),
  (-2, 'George'),
  (-3, 'Harry'),
  (-4, 'Ron'),
  (-5, 'Hermine');

INSERT INTO task (ID, NAME, DESCRIPTION)
VALUES (-1, 'Brew Potion','Brew potion according to book'),
       (-2, 'Study','Study for upcoming exam');

INSERT INTO task (ID, NAME, DESCRIPTION, STATUS, ASSIGNEE, TIME_MINUTES, IS_SUBTASK_OF)
VALUES (-1, 'Cut mushrooms','Use knife to cut mushrooms','TODO',-3,0,-1),
       (-2, 'Stir pot','Use ladle to stir','TODO',-3,0,-1),
       (-3, 'Read Books','Mark important sections','DOING',-5,10,-2),
       (-4, 'Play Pranks','Setup prank and execute','DONE',-2,200,NULL);

