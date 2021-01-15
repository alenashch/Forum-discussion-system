package nl.tudelft.sem.group20.contentserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Post extends Content {

    private int postNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardThread_id", referencedColumnName = "id")
    @JsonIgnore
    //@JsonManagedReference
    private BoardThread boardThread;

    public Post() {
        super();
    }

    /**
     * Non-empty constructor for Post class.
     *
     * @param id         - id of the Post.
     * @param postNumber - the number of the Post (to identify it within a thread).
     * @param creatorName - the name of the thread creator.
     * @param body       - the body of a Post.
     * @param created    - LocalDateTime showing when a Post was created.
     */
    public Post(long id, int postNumber, String creatorName, String body,
                BoardThread boardThread, LocalDateTime created) {

        super(id, body, creatorName, created);
        this.postNumber = postNumber;
        this.boardThread = boardThread;
    }

    /**
     * Non-empty constructor for Post class.
     *
     * @param postNumber - the number of the Post (to identify it within a thread).
     * @param body       - the body of a Post.
     * @param created    - LocalDateTime showing when a Post was created.
     */
    public Post(int postNumber, String creatorName, String body, BoardThread boardThread,
                LocalDateTime created) {
        super(body, creatorName, created);
        this.postNumber = postNumber;
        this.boardThread = boardThread;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public boolean isEdited() {
        return !this.getEditedTime().isEqual(this.getCreatedTime());
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
        return this.getId() == post.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), postNumber, getBody(),
                getCreatedTime(), getEditedTime());
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + getId()
                + ", postNumber=" + postNumber
                + ", body='" + getBody() + '\''
                + ", created=" + getCreatedTime()
                + ", edited=" + getEditedTime()
                + '}';
    }
}