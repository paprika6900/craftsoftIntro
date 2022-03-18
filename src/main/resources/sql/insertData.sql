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

INSERT INTO task_group (ID, NAME, DESCRIPTION)
VALUES (-1, 'Brew Potion','Brew potion according to book'),
       (-2, 'Study','Study for upcoming exam');

INSERT INTO task (ID, NAME, DESCRIPTION, STATUS, ASSIGNEE, TIME_MINUTES, IS_SUBTASK_OF_GROUP, IS_SUBTASK_OF_TASK)
VALUES (-1, 'Add Mushrooms','Add Mushrooms to Cauldron','TODO',-3,0,-1,NULL),
       (-2, 'Stir pot','Use ladle to stir','TODO',-3,0,-1,NULL),
       (-3, 'Read Books','Mark important sections','DOING',-5,10,-2,NULL),
       (-4, 'Play Pranks','Setup prank and execute','DONE',-2,200,NULL,NULL);

INSERT INTO task (ID, NAME, DESCRIPTION, STATUS, ASSIGNEE, TIME_MINUTES, IS_SUBTASK_OF_GROUP, IS_SUBTASK_OF_TASK)
VALUES (-5, 'Cut Mushrooms', 'Wash and cut mushrooms into pieces', 'TODO', -3,0,NULL,-1),
       (-6, 'Sprinkle magic dust', 'Add sand', 'TODO', -3,0,NULL,-1);

INSERT INTO task (ID, NAME, DESCRIPTION, STATUS, ASSIGNEE, TIME_MINUTES, IS_SUBTASK_OF_GROUP, IS_SUBTASK_OF_TASK)
VALUES (-7, 'Wash Knife', 'Wash the Knife before use', 'TODO', -3,0,NULL,-5);

