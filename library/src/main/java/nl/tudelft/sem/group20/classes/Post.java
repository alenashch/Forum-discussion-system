package nl.tudelft.sem.group20.classes;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {

    private long id;

    private int postNumber;

    private String body;

    private LocalDateTime created;

    private LocalDateTime edited;

    public Post() {
    }

    /**
     * Non-empty constructor for Post class.
     *
     * @param id - id of the Post.
     * @param postNumber - the number of the Post (to identify it within a thread).
     * @param body - the body of a Post.
     * @param created - LocalDateTime showing when a Post was created.
     */
    public Post(long id, int postNumber, String body, LocalDateTime created) {
        this.id = id;
        this.postNumber = postNumber;
        this.body = body;
        this.created = created;
        //initially, set this field the same as the created field
        this.edited = created;
    }

    /**
     * Non-empty constructor for Post class.
     *
     * @param postNumber - the number of the Post (to identify it within a thread).
     * @param body - the body of a Post.
     * @param created - LocalDateTime showing when a Post was created.
     */
    public Post(int postNumber, String body, LocalDateTime created) {
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

    public void setEdited(LocalDateTime edited) {

        this.edited = edited;
    }

    public LocalDateTime getEdited() {
        return edited;
    }

    public boolean isEdited() {
        return !this.edited.isEqual(this.created);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postNumber, body, created, edited);
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", postNumber=" + postNumber
                + ", body='" + body + '\''
                + ", created=" + created
                + ", edited=" + edited
                + '}';
    }
}




