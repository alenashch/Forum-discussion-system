package nl.tudelft.sem.template.entities;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    public Board() {

    }

    /**
     * Non-empty constructor for Board class
     *
     * @param id Unique identifier as to be used in the database
     * @param name The name of the board
     * @param description The description of the board
     */

    public Board(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
        return Objects.hash(id, name, description);
    }


    @Override
    public String toString() {
        return "Board{"
                + "boardId='" + id + '\''
                + ", boardName='" + name + '\''
                + ", boardDescription='" + description + '\''
                + '}';
    }
}
