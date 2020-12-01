package nl.tudelft.sem.group20.boardserver.entities;

import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    private boolean locked;

    private LocalDateTime edited;

    transient private LocalDateTime created;

    public Board() {
        //this was causing a bug in the system.
            //please message me if you don't get why.
            //way to go is check if field is null once the
            //object is created and if null add a time during
            //processing of the request.
            //-Pietro
        //this.created = LocalDateTime.now();
        //this.edited = created;
    }

    /**
     * Non-empty constructor for Board class.
     *
     * @param id Unique identifier as to be used in the database.
     * @param name The name of the board.
     * @param description The description of the board.
     * @param locked Indicates whether the board is locked or not.
     */

    public Board(long id, String name, String description, boolean locked) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.locked = locked;
        this.created = LocalDateTime.now();
        this.edited = created;
    }

    /**
     * For testing purposes: constructor with no id.
     *
     * @param name - board name.
     * @param description - details about the board.
     * @param locked - true if a board is locked, false otherwise.
     */
    public Board(String name, String description, boolean locked) {
        this.name = name;
        this.description = description;
        this.locked = locked;
        this.created = LocalDateTime.now();
        this.edited = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
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

    public boolean isEdited() {
        return !this.edited.isEqual(this.created);
    }

    /**
     * This method sets the edited field to a new value.
     *
     * @param edited - LocalDateTime representing when the Board was last edited.
     * @throws IllegalArgumentException when the new "edited" value is before the
     *         current edited value.
     */
    public void setEdited(LocalDateTime edited) throws IllegalArgumentException {
        if ( this.edited == null || edited.isAfter(this.edited) || edited.isEqual(this.edited)) {
            this.edited = edited;
        } else {
            throw new IllegalArgumentException(
                    "Board cannot be edited before it was last edited.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board = (Board) o;
        return Objects.equals(id, board.getId());
    }

    public static void checkCreationTime(Board board){
        if(board.getCreated()==null){
            board.setCreated(LocalDateTime.now());
            board.setEdited(LocalDateTime.now());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, locked);
    }


    @Override
    public String toString() {
        return "Board{"
                + "boardId='" + id + '\''
                + ", boardName='" + name + '\''
                + ", boardDescription='" + description + '\''
                + ", locked='" + locked + '\''
                + ", edited='" + edited + '\''
                + ", created='" + created + '\''
                + '}';
    }
}
