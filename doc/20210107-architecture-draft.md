#Intro
As the component diagram of our system shows, our application is divided into three 
microservices: the Authentication Server, the Board Server, and the Content Server. 
Each of these is built using the Spring framework: there is a repository which simplifies c
ommunication with the database, a service which handles the logic of processing a 
request, and a controller which calls the service methods and provides API endpoints. 
Details regarding each of the microservices and how they communicate with each other are outlined below.

#Boards
**Responsibility:** As the name suggests, the Board Microservice is responsible for boards. 
This means that it enables creation, retrieval, and updating of these objects 
(deletion is omitted, as it is not part of the system requirements). 
It is also worth mentioning that the parameters passed to the service methods 
are not in fact instances of the Board class, but of other classes created 
specifically for requests, which sometimes omit certain details such as the id of the 
board or the user who created it, which can be determined in other ways.

**Role:** The responsibilities of this microservice are not only internal. 
The Board Microservice communicates with other microservices. 
These relationships are illustrated by the component diagram of our system. 
First of all, we can see that the Board Server requires the interface provided 
by the Authentication Server. There are some logical reasons behind this. We need 
to check that a user trying to create or edit a board is a registered user, and that 
they have permission to do so. In order to do this, the BoardService class uses 
Spring’s RestTemplate to make calls to the Authentication Server. As explained in
the section related to the Authentication Server, the responses are objects from 
the shared module, and they provide information for the Board Microservice to 
determine if the user is a teacher, or if he/she is the creator of a board. 
Unsuccessful requests throw exceptions, which are handled by a ControllerAdvice class. 
An adequate ResponseEntity is returned, containing the error message, or, in case of 
success, a confirmation, and an HTTP status code. Secondly, the Board Microservice 
provides an interface for the Content Microservice. This consists of one method, 
which specifies whether a board is locked or not. A board that is locked would 
no longer allow the creation of threads associated with it. 
 
 **Motivation:** There are many ways to justify the need for a standalone microservice for 
boards. Unlike threads and posts, which represent the content, boards are mostly used to 
organize this content depending on its topic. Hence, they are not as tightly 
coupled as threads and posts, and, while they certainly play an important role in 
making the forum more readable, an early prototype could be released even if the Board 
Microservice is not fully implemented. We also thought about future developments of our 
application, and one that naturally comes to mind is to introduce one more component: 
courses. It makes sense that each course has mutiple boards (perhaps for each lecture, 
or each week), and the Board Microservice could thus be updated to take on more 
responsibilities. Had we implemented boards, threads, and posts as one server, 
this addition would make it overcrowded.
As explained in the section regarding the Authentication Server, it also benefits 
the maintainability of the system to keep the users in a separate microservice, 
therefore it is clear why boards and users are separated.