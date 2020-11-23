package nl.tudelft.sem.template.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.entities.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BoardTest {
    transient Board board;

    transient long id;
    transient String name;
    transient String description;
    transient Board board2;
    transient Board board2Copy;


    @BeforeEach
    void initialize() {
        board = new Board();

        id = 2;
        name = "Board 2";
        description = "Description of board 2";

        board2 = new Board(id, name, description);
        board2Copy = new Board(board2.getId(), board2.getName(), board2.getDescription());

    }

    @Test
    void testEmptyConstructor() {
        assertNotNull(board);
    }

    @Test
    void testNonEmptyConstructor() {
        assertNotNull(board2);
        assertTrue(board2.getId() == id
                && board2.getName().equals(name)
                && board2.getDescription().equals(description));
    }


    @Test
    public void testGetAndSetId() {
        board.setId(3);
        assertEquals(3, board.getId());
    }


    @Test
    public void testGetAndSetName() {
        board.setName("New Board 2");
        assertEquals("New Board 2", board.getName());
    }

    @Test
    public void testGetAndSetDescription() {
        board.setDescription("New description of board 2");
        assertEquals("New description of board 2", board.getDescription());
    }

    @Test
    public void testBoardTwoEqual() {
        assertTrue(board2.equals(board2Copy));
    }

    @Test
    void testDifferentHashCodes() {
        assertNotEquals(board.hashCode(), board2.hashCode());
    }

    @Test
    void testSameHashCodes() {
        assertEquals(board2.hashCode(), board2Copy.hashCode());
    }


    @Test
    public void testBoardToString() {
        String boardToString = "Board{boardId='" + board.getId()
                + "', boardName='" + board.getName()
                + "', boardDescription='" + board.getDescription()
                + "'}";
        assertEquals(board.toString(), boardToString);
    }
}
