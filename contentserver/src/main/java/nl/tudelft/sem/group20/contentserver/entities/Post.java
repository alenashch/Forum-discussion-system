package nl.tudelft.sem.group20.contentserver.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import nl.tudelft.sem.group20.contentserver.serialization.LocalDateTimeDeserializer;
import nl.tudelft.sem.group20.contentserver.serialization.LocalDateTimeSerializer;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int postNumber;

    private String body;

    private String creator;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime created;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime edited;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id", referencedColumnName = "id")
    @JsonManagedReference
    private BoardThread boardThread;

    public Post() {
    }

    /**
     * Non-empty constructor for Post class.
     *
     * @param id         - id of the Post.
     * @param postNumber - the number of the Post (to identify it within a thread).
     * @param body       - the body of a Post.
     * @param created    - LocalDateTime showing when a Post was created.
     */
    public Post(long id, String creator, int postNumber, String body, BoardThread boardThread,
                LocalDateTime created) {
        this.id = id;
        this.postNumber = postNumber;
        this.body = body;
        this.created = created;
        //initially, set this field the same as the created field
        this.edited = created;
        this.boardThread = boardThread;
        this.creator = creator;
    }

    /**
     * Non-empty constructor for Post class.
     *
     * @param postNumber - the number of the Post (to identify it within a thread).
     * @param body       - the body of a Post.
     * @param created    - LocalDateTime showing when a Post was created.
     */
    public Post(int postNumber, String creator, String body, BoardThread boardThread,
                LocalDateTime created) {
        this.postNumber = postNumber;
        this.body = body;
        this.created = created;
        //initially, set this field the same as the created field
        this.edited = created;
        this.creator = creator;
        this.boardThread = boardThread;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setBoardThread(BoardThread boardThread) {

        this.boardThread = boardThread;
    }

    public BoardThread getBoardThread() {

        return this.boardThread;
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




