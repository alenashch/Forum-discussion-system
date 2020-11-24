package nl.tudelft.sem.template.model;

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

    transient private LocalDateTime created;

    private LocalDateTime edited;

    public Board() {
        this.created = LocalDateTime.now();
        this.edited = created;
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
     * @throws IllegalArgumentException when the new "edited" value is before
     *                                  the current edited value.
     */
    public void setEdited(LocalDateTime edited) throws IllegalArgumentException {
        if (edited.isAfter(this.edited) || edited.isEqual(this.edited)) {
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
                + '}';
    }
}
