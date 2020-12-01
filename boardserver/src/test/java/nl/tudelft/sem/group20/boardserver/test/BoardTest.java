package nl.tudelft.sem.group20.boardserver.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import nl.tudelft.sem.group20.boardserver.entities.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
/*
@SpringBootTest(classes = Board.class)
public class BoardTest {
    transient Board board;

    transient long id;
    transient String name;
    transient String description;
    transient boolean locked;
    transient LocalDateTime invalidEdited;
    transient LocalDateTime validEdited;
    transient Board board2;
    transient Board board2Copy;


    @BeforeEach
    void initialize() {
        board = new Board();

        id = 2;
        name = "Board 2";
        description = "Description of board 2";
        locked = false;

        board2 = new Board(id, name, description, locked);
        board2Copy = new Board(board2.getId(), board2.getName(), board2.getDescription(),
                board2.getLocked());

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
                && board2.getDescription().equals(description)
                && board2.getLocked() == locked);
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
    public void testGetAndSetLocked() {
        board.setLocked(true);
        assertEquals(true, board.getLocked());
    }

    //@Test
    public void testGetCreated() {
        assertTrue(board.getCreated().isEqual(LocalDateTime.now())
                || board.getCreated().isBefore(LocalDateTime.now()));
    }

    //@Test
    void testSetAndGetEditedSuccessful() {
        validEdited = board.getCreated().plusHours(3);
        board.setEdited(validEdited);
        assertEquals(validEdited, board.getEdited());
    }

    //@Test
    void testSetEditedException() {
        invalidEdited = board.getCreated().minusHours(3);
        assertThrows(IllegalArgumentException.class, () -> board.setEdited(invalidEdited));
    }

    @Test
    void testIsEditedFalse() {
        assertFalse(board2.isEdited());
    }


    @Test
    public void testBoardTwoEqual() {
        assertTrue(board2.equals(board2Copy));
    }

    @Test
    public void testBoardTwoNotEqual() {
        assertFalse(board.equals(board2));
    }

    @Test
    void testDifferentHashCodes() {
        assertNotEquals(board.hashCode(), board2.hashCode());
    }

    @Test
    void testSameHashCodes() {
        assertEquals(board2.hashCode(), board2Copy.hashCode());
    }


    //@Test
    public void testBoardToString() {
        String boardToString = "Board{boardId='" + board.getId()
                + "', boardName='" + board.getName()
                + "', boardDescription='" + board.getDescription()
                + "', locked='" + board.getLocked()
                + "', edited='" + board.getEdited()
                + "'}";
        assertEquals(board.toString(), boardToString);
    }
}*/
