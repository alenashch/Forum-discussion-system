# Sprint Retrospective, Iteration #1

| User Story/Issue                       | task # | Estimated Effort per Task | Actual Effort per Task | Done | Notes                                                        |
| -------------------------------- | ------ | ------------------------- | ---------------------- | ---- | ------------------------------------------------------------ |
| Users can create threads         | 1      | 1 hour                    | 1 hour                 | Yes  |                                                              |
| User can get all threats         | 1      | 1 hour                    | 1 hour                 | Yes  |                                                              |
| Database is connected to project | 16     | 1 hour                    | 1 hour                 | Yes  | Project Infrastructure                                       |
| Eureka Server for microservices  | 28     | 2 hours                   | 2 hours                | Yes   | Project Infrastructure. Need some more work for it to run, but initial set up is up and running |
| User can create boards           | 15     | 3 hours                   | 4 hours                | Yes  |                                                              |
| User can get all boards          | 14     | 3 hours                   | 4 hours                | Yes  |                                                              |
| Users can be created             | 3      | 3 hours                   | 7 hours                | Yes  |                                                              |
| Users can get info about users   | 5      | 2 hours                   | 3 hours                | Yes  |                                                              |
| Microservice architecture        | 36     | 6 hours                   | 7 hours                | Yes  | Added later because of new information of lecture
| Security                         | 13     | 3 hours                   | - hours                | No   | Not added because of change in architecture of project
| Users can manage posts           | 11     | 2 hours                   | 6 hours                | Yes  | Took longer than expected because it was the first created controller |
| Users can manage boards          | 2      | 2 hours                   | 2 hours                | Yes  |
| Admins can manage users          | 6      | 2 hours                   | 3 hours                | Yes  | Had some terrible issues designing tests|
| Users can manage threads         | 9      | 2 hours                   | 2 hours                | Yes  | |     
| Create Post Repository        | 4      | 3 hours                   | 6 hours                | Yes  | This took long because I had to set up the project.|  
| Create Post Service        | 10      | 2 hours               | 3 hours 30 minutes                | Yes  | |  



**Project:** Student/Teacher forum

**Group:** OP10-SEM20



# Main Problems Encountered

**Problem 1**

- Description: Setting up the Eureka server is just in its infancy. There seems to be some error in the connections. This could be a problem from the dependencies.
- Reaction: This set up has to be completed. At least we were able to set-up and run the Eureka Server. More has to be developed in the coming days

**Problem 2**

- Description: Now that we are aware of the architecture, we have to set up our structure and determine what should correspond as a micro service and what not.
- Reaction: Group planning and careful infrastructure review

**Problem 3**

- Description: Issues assigned to different people depended on each other.
- Reaction: A lot of team members could not start writing code until others were finished with their tasks. For instance, the controllers depended on the services and repositories to be finished.


## Adjustments for next Sprint Plan

- So far the team has reacted well, we now have to shift our focus on the infrastructure so that we can plan better what each micro service will need, and how to coordinate communication within each micro service
- We also need to determine what corresponds as a micro service or not.
- Split issues in such a way that everyone can start working at the beginning of a sprint, not just 2 days before the sprint ends.