package nl.tudelft.sem.template.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Each topic can have multiple threads consisting of posts.
//Each thread starts with a question or statement and can have multiple posts replying on it.
//Threads and posts can be created by both teachers and students.
//Threads have a title displaying the purpose of the thread, a
//   body containing the question or statement, the creator of the thread, and the time it was created
//Topics, Threads, and individual post should all be uniquely linkable so that users can
//   share links to these resources with other people.
//Teachers are able to lock and unlock a topic or thread to prevent users from adding new content
//However, users should be able to edit their topics, threads, and posts.



@Entity
public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}