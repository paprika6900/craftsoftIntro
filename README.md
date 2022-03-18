# craftsoftIntro
The Introduction example of craftsoft

Local Execution Instructions:
run in commandline as: mvnw spring-boot:run 

Design Choices:
As this is a relatively small application I wrote the Junit tests in Post and did not apply TDD. 

I have chosen not to use hibernate, since I wanted to refresh on sql and did not know if hibernate would have been indicative of requirements. 

I chose the Database design, based on a short analysis of the requirements. 
For the sake of this example I have decided to make some assumptions:
Task groups are literal groupings of tasks. That can have names and descriptions
Tasks may or may not have subtasks.
The state of a task can only be TODO DOING and DONE.

I have added the Employee classes and table to keep it expandable. 
Another way to mock this, could have been an enum.

Services and Daos have Interfaces for easy exchange when necessary. 

Further Ideas and Improvements:
Depending on use case Hide internal Ids.
Add additional Verification as currently a lot of potential bad Input is trusted!
Add additional DTOs, since it is probably unnecessary to construct all Subtasks each time!
Add additional DTOs including Employee names instead of IDs only.
Change System of testdata-Insertion.
Possibly use hibernate instead. 
Add functionality into service to set task to correct state based on subtasks
Add functionality into service to add up all time of subtasks
Additional Endpoints for taskgroups
Additional Tests