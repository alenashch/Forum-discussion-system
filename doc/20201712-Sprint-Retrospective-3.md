# Sprint Retrospective, Iteration #3

| User Story/Issue                 | task # | Estimated Effort per Task | Actual Effort per Task | Done | Notes                                                        |
| -------------------------------- | ------ | ------------------------- | ---------------------- | ---- | ------------------------------------------------------------ |
| Add integration testing to AuthenticationService | 77    | 20 min                    | 30 min                 | Yes  |                                                           |
| Make shared library submodule and shared Request response classes | 59    | 1 hour 30 min                   | 2 hours              | Yes  |                                                           |
| Add Authentication Support for Board Microservice | 50    | 3 hours              | 8 hours              | Yes  | 
| Add request classes for Board microservice | 69    | 1 hours              | 3 hours              | Yes  |
| Add tests for BoardControllerAdvice | 72    | 20 minutes              | 20 minutes              | Yes  |
| Make posts linkable | 60 | 1 hour | 1 hour 30 minutes | Yes |
| Add isEdit method to the post controller | 67 | 1 hour | 1 hour | Yes | We don't have to keep isEdited field in Post entity having this method |
| Tests the new forms of requests | 57 | 1 hour | 1 hour | Yes | |
| Create method for locking and unlcoking threads | 61 | 1 hour | 1 hour 30 minutes | Yes| |
| Add constraint check to the content Microservice | 56 | 4 hours | 4 hours | Yes | |
| Unit Testing AuthenticationService | 82 | 30 minutes | 40 minutes | Added missing tests
| Added controller testing for thread controller | 76 | 2 hours | 2 hours | Yes | |
| Added service testing for thread controller | 76 | 2 hours | 3 hours | Yes | |

**Project:** Student/Teacher forum

**Group:** OP10-SEM20



# Main Problems Encountered

**Problem 1**
- Description: Different teams were working on different microservices, and not all conventions were established beforehand.
- Reaction: There is a bit of inconsistency between different microservers. Nonetheless, they work together just fine.

**Problem 2**
- Description: Some method were not tested properly and the existing tests left a lot of space for bugs.
- Reaction: Make sure every merge request contains code that is well tested.

**Problem 3**
- Description: It was difficult to divide the work, as a lot of the things still needing to be done surfaced only when we started. Some people had to wait for others to finnish.
- Reaction: People asked what still needed to be done and picked up the tasks. 

## Adjustments for next Sprint Plan
- I believe planning went better than in previous weeks, but we can still improve. Perhaps more communication and more careful planning before coding will help.
- More active communication from all members would have been better
- More active reviewers for merge request would have also improved our code quality