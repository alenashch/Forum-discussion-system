package nl.tudelft.sem.group20.contentserver.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import nl.tudelft.sem.group20.contentserver.serialization.LocalDateTimeDeserializer;
import nl.tudelft.sem.group20.contentserver.serialization.LocalDateTimeSerializer;

@Entity(name = "thread")
@Table(name = "thread")
public class BoardThread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String threadTitle;    //title of thread

    private String statement;      //Main question or statement of thread

    private String threadCreator;  //name of thread creator

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime created; //when was it creates

    private boolean locked;        //locked thread or not

    private long boardId;          //board it belongs to

    @OneToMany(mappedBy = "boardThread", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    Set<Post> posts = new HashSet<>();


    /**
     * Empty constructor of Board Thread
     */

    public BoardThread() {

    }

    /**
     * Non-empty constructor of BoardThread.
     *
     * @param id              id of item
     * @param threadTitle     title of thread
     * @param statement       general statment of thread
     * @param threadCreatorId person who created thread
     * @param locked          locked or not
     */
    public BoardThread(Long id, String threadTitle, String statement, String threadCreatorId,
                       LocalDateTime created,
                       boolean locked) {
        this.id = id;
        this.threadTitle = threadTitle;
        this.statement = statement;
        this.threadCreator = threadCreatorId;
        this.created = created;
        this.locked = locked;
    }

    /**
     * Non-empty constructor of BoardThread.
     *
     * @param threadTitle     title of thread
     * @param statement       general statment of thread
     * @param threadCreatorId person who created thread
     * @param locked          locked or not
     */
    public BoardThread(String threadTitle, String statement, String threadCreatorId,
                       LocalDateTime created, boolean locked) {
        this.threadTitle = threadTitle;
        this.statement = statement;
        this.threadCreator = threadCreatorId;
        this.created = created;
        this.locked = locked;
    }

    /**
     * Non-empty constructor of BoardThread.
     *
     * @param threadTitle     title of thread
     * @param statement       general statment of thread
     * @param threadCreatorId person who created thread
     * @param locked          locked or not
     */
    public BoardThread(String threadTitle, String statement, String threadCreatorId,
                       LocalDateTime created, boolean locked, long boardId) {
        this.threadTitle = threadTitle;
        this.statement = statement;
        this.threadCreator = threadCreatorId;
        this.created = created;
        this.locked = locked;
        this.boardId = boardId;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getThreadTitle() {
        return threadTitle;
    }

    public void setThreadTitle(String name) {
        this.threadTitle = name;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public long getBoardId() {
        return boardId;
    }

    public String getThreadCreator() {
        return threadCreator;
    }

    public void setThreadCreator(String threadCreator) {
        this.threadCreator = threadCreator;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BoardThread that = (BoardThread) o;
        return id == that.id;
    }

    public void addPost(Post post) {

        posts.add(post);
    }

    public void removePost(Post post) {

        posts.remove(post);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Thread{"
            + "id=" + id
            + ", threadTitle='" + threadTitle + '\''
            + ", statement='" + statement + '\''
            + ", threadCreatorId='" + threadCreator + '\''
            + ", created=" + created
            + ", locked=" + locked
            + '}';
    }

}