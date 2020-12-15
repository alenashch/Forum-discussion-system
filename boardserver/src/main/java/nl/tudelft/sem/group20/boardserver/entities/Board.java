package nl.tudelft.sem.group20.boardserver.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private boolean locked;

    @UpdateTimestamp
    @Column
    private LocalDateTime edited;

    @CreationTimestamp
    @Column
    transient private LocalDateTime created;

    public Board() {

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
     * Constructor with no id.
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

    public void setEdited(LocalDateTime edited) {
        this.edited = edited;
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
