# Sprint Retrospective, Iteration #2

| User Story/Issue                 | task # | Estimated Effort per Task | Actual Effort per Task | Done | Notes                                                        |
| -------------------------------- | ------ | ------------------------- | ---------------------- | ---- | ------------------------------------------------------------ |
| Refactor BoardController and/or BoardService Responses | 49    | 1 hour 30 min                    | 1 hour 30 min                 | Yes  |                                                           |
| Add Authentication Support for Board Microservice | 50    | 3 hours                    | -                 | No  | This issue depended on methods of the authentication service, and will have to be done during the next sprint.                                                          |
| Fix BoardController tests | 47    | 2 hours                     | 6 hours                 | Yes  |  It took much longer as there were issues with entities, which affected BoardController tests. It took a lot of time to find them.            |
| Enhance Board API | 48    | 2 hours                     | 2 hours                 | Yes  |                             |
| Repair eureka clients issues | n/a | 2 hours | 8 hours | Yes | A lot of dependency issues and really tough dependency debugging. But now all eureka clients are recognized and they communicate with the eureka server. |
| Repair testing dependencies and bugs | n/a | 2 hours | 2 hours | Yes | There were bugs in the set up the tests and also bugs in the way the Java and Spring were parsing our date items. |
| Connect Thread and Posts to SQL database | 44 | 1 hours | 1 hours | Yes | |
| Finish and test Thread Controller | 41 | 2 hours | 2 hours | Yes | |
| Finish and test Thread Service | 41 | 1 hours | 1 hour | yes | |



**Project:** Student/Teacher forum

**Group:** OP10-SEM20



# Main Problems Encountered

**Problem 1**

- Description: The same as in the last sprint, issues depended on each other.
- Reaction: Some tasks could not be completed, because they depended on others to be done.

**Problem 2**

- Description: Work started too late, a couple of days before the end of the sprint.
- Reaction: Merge requests were chaotic, code review could not be done properly.

**Problem 3**

- There were a lot of dependency bugs and faulty code in our initial project structure. This set us back fro a lot of hours (more than we had anticipated) since we had to make our components work before even starting to code them. 
- These bugs were rather difficult to fix since the error messages were very non-descriptive.


## Adjustments for next Sprint Plan

- I think setting deadlines for issue and actually following them will help those who have to build upon the work of others.
- Some teammates should improve their communication.
- Sprint planning has to be done more carefully, issues should be present from the first day of the sprint, so that anyone can start working on them the following day.