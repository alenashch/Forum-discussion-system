package nl.tudelft.sem.template.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int postNumber;

    private String body;

    private LocalDateTime created;

    private LocalDateTime edited;

    public Post() {
    }

    public Post(long id, int postNumber, String body, LocalDateTime created) {
        this.id = id;
        this.postNumber = postNumber;
        this.body = body;
        this.created = created;
        //initially, set this field the same as the created field
        this.edited = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getEdited() {
        return edited;
    }

    public void setEdited(LocalDateTime edited) throws IllegalArgumentException {
        if (edited.isAfter(this.created))
            this.edited = edited;
        else
            throw new IllegalArgumentException("Post cannot be edited before or when it was created.");
    }

    public boolean isEdited() {
        return !this.edited.isEqual(this.created);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", postNumber=" + postNumber +
                ", body='" + body + '\'' +
                ", created=" + created +
                ", edited=" + edited +
                '}';
    }
}
