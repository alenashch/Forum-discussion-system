package nl.tudelft.sem.group20.contentserver.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

@Entity(name = "thread")
@Table(name = "thread")
public class BoardThread extends Content{

    private String threadTitle;    //title of thread

    private boolean locked;        //locked thread or not

    private long boardId;          //board it belongs to

    private transient boolean isEdited; //boolean that tells if post is edited

    @OneToMany(mappedBy = "boardThread", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    Set<Post> posts = new HashSet<>();


    /**
     * Empty constructor of Board Thread.
     */

    public BoardThread() {
        super();
    }

    /**
     * Non-empty constructor of BoardThread.
     *
     * @param id              id of item
     * @param threadTitle     title of thread
     * @param locked          locked or not
     */
    public BoardThread(Long id, String threadTitle, String body, String creatorName,
                       LocalDateTime created, boolean locked, long boardId, boolean isEdited) {

        super(id, body, creatorName, created);
        this.threadTitle   = threadTitle;
        this.locked        = locked;
        this.boardId       = boardId;
        this.isEdited      = isEdited;
    }

    /**
     * Non-empty constructor of BoardThread.
     *
     * @param threadTitle     title of thread
     * @param locked          locked or not
     * @param boardId         id of the board this thread is assigned to
     */
    public BoardThread(String threadTitle, String body, String creatorName,
                       LocalDateTime created, boolean locked, long boardId) {

        super(body, creatorName, created);
        this.threadTitle = threadTitle;
        this.locked = locked;
        this.boardId = boardId;
    }

    public String getThreadTitle() {
        return threadTitle;
    }

    public void setThreadTitle(String name) {
        this.threadTitle = name;
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


    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }


    public boolean isThreadEdited() {
        return isEdited || !this.getEditedTime().isEqual(this.getCreatedTime());
    }


    public void setIsThreadEdited(boolean edited) {
        isEdited = edited;
    }

    public void addPost(Post post) {

        posts.add(post);
    }

    public void removePost(Post post) {

        posts.remove(post);
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
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "BoardThread{"
                + "id=" + this.getId()
                + ", threadTitle='" + threadTitle + '\''
                +  ", statement='" + this.getBody() + '\''
                +  ", threadCreator='" + this.getCreatorName() + '\''
                +  ", createdTime=" + this.getCreatedTime()
                + ", editedTime=" + this.getEditedTime()
                + ", locked=" + locked
                + ", boardId=" + boardId
                + ", isEdited=" + isEdited
                + ", posts=" + posts
                + '}';
    }
}