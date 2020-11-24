## Description

The Department of Software Technology at TU Delft wants to boost students' learning experience outside of class. For that reason, the development of an online discussion forum is envisioned to help students to review material before an assignment or exam, engaging students in a discussion of the course material before coming to class, and reflecting on material that they have read or worked with outside of class.

## Technical Specifications

The system should be built in such a way that it can be extended with extra functionalities later (modular) and allow for easy integration with other systems (API). Individual components of the system need to be scalable so that when the demand for that service increases, the service does not get overloaded (microservices). The system should be written in the Java programming language (version 11). The system should not have a graphical user interface (GUI), but instead, all interactions with the systems are handled through the APIs. The system must be built with Spring Boot (Spring framework) and Gradle. 

## Security Specifications

All users of the system need to authenticate themselves to determine who they are and what they can do on the platform. Users need to have a NetID associated with their account to identify them across the systems of the TU Delft universally. This NetID, together with a password, serves as the credentials for the account. The password needs to be stored safely. For simplicity, the system will not be connected to the existing TU Delft authentication system (Single-Sign-On). The NetID is just a unique string used to identify each user. Security is a difficult aspect of the program to get right; therefore, we strongly advise to use Spring Security.

## Domain Specifications

The forum board system should have a different board for each topic. These topics can, for example, be different courses or different parts of a course. Each topic can have multiple threads consisting of posts. Each thread starts with a question or statement and can have multiple posts replying on it. Topics can only be created by teachers. Threads and posts can be created by both teachers and students. Anonymous users should be able to access the forum board and read threads; however, they shouldn't be able to create or post anything.

Forum board topics have a name and description that should be given to the user when they request the board. Threads have a title displaying the purpose of the thread, a body containing the question or statement, the creator of the thread, and the time it was created. Each post has the users creating the post, the time it was posted, and the body of the post. Topics, Threads, and individual post should all be uniquely linkable so that users can share links to these resources with other people.

Teachers are able to lock and unlock a topic or thread to prevent users from adding new content. If a topic or thread is locked, it is still visible to all users. However, users should be able to edit their topics, threads, and posts. When a topic, thread, or post is edited, it should display that it is edited and when the last edit was made.

All users of the system need to authenticate themselves to determine who they are and what they can do on the platform. Users need to have a NetID associated with their account to identify them across the systems of the TU Delft universally. This NetID, together with a password, serves as the credentials for the account. The password needs to be stored safely. For simplicity, the system will not be connected to the existing TU Delft authentication system (Single-Sign-On). The NetID is just a unique string used to identify each user. Security is a difficult aspect of the program to get right; therefore, we strongly advise to use Spring Security.

## Final Remarks

Your task is to model and develop the system based on the above summary of the client. Extract the functionality and constraints from the summary and write those down as requirements. Your TA will check these requirements to make sure you are on the right track. If you find requirements that are incomplete or require further information, please use the Brightspace discussion forum to ask your question to the client.

