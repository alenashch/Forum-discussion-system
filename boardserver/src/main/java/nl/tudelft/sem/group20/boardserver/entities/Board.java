package nl.tudelft.sem.group20.boardserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "board")
public class Board extends ExtendedBaseEntity {

    @Column
    private String description;

    @Column
    private boolean locked;

    public Board() {
        super();
    }

    /**
     * Non-empty constructor for Board class.
     *
     * @param id Unique identifier as to be used in the database.
     * @param name The name of the board.
     * @param description The description of the board.
     * @param locked Indicates whether the board is locked or not.
     */

    public Board(long id, String name, String description, boolean locked, String username) {
        super(id, name, username);
        this.description = description;
        this.locked = locked;
    }

    /**
     * Constructor with no id.
     *
     * @param name - board name.
     * @param description - details about the board.
     * @param locked - true if a board is locked, false otherwise.
     */
    public Board(String name, String description, boolean locked, String username) {
        super(name, username);
        this.description = description;
        this.locked = locked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Board) {
            Board that = (Board) o;
            return super.equals(o)
                    && this.locked == that.locked
                    && this.description.equals(that.description);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName(),
                this.description, this.getTimestampTracker(), this.getUsername(),
                this.locked);
    }

    @Override
    public String toString() {
        return "Board{"
                + "boardId='" + this.getId() + '\''
                + ", boardName='" + this.getName() + '\''
                + ", boardDescription='" + this.description + '\''
                + ", locked='" + this.locked + '\''
                + ", " + this.getTimestampTracker().toString() + '\''
                + ", username='" + this.getUsername() + '\''
                + '}';
    }
}
