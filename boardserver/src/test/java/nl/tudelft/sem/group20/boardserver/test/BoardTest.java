package nl.tudelft.sem.group20.boardserver.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import nl.tudelft.sem.group20.boardserver.embeddable.TimestampTracker;
import nl.tudelft.sem.group20.boardserver.entities.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Board.class)
public class BoardTest {
    transient Board board;

    transient long id;
    transient String name;
    transient String description;
    transient boolean locked;
    transient LocalDateTime validEdited;
    transient TimestampTracker timestampTracker;
    transient String user;
    transient Board board2;
    transient Board board2Copy;

    @BeforeEach
    void initialize() {
        board = new Board();
        Board.checkCreationTime(board);


        id = 2;
        name = "Board 2";
        description = "Description of board 2";
        locked = false;
        user = "user";

        timestampTracker = new TimestampTracker();

        board2 = new Board(id, name, description, locked, user);
        board2Copy = new Board(board2.getId(), board2.getName(), board2.getDescription(),
                board2.isLocked(), user);
        board2Copy.setTimestampTracker(board2.getTimestampTracker());
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
                && board2.isLocked() == locked);
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
        assertEquals(true, board.isLocked());
    }

    @Test
    public void testGetAndSetUsername() {
        board.setUsername("user2");
        assertEquals("user2", board.getUsername());
    }

    @Test
    public void testGetCreated() {
        assertTrue(board.getTimestampTracker().getCreated().isEqual(LocalDateTime.now())
                || board.getTimestampTracker().getEdited().isBefore(LocalDateTime.now()));
    }

    @Test
    void testSetAndGetEditedSuccessful() {
        validEdited = board.getTimestampTracker().getCreated().plusHours(3);
        board.getTimestampTracker().setEdited(validEdited);
        assertEquals(validEdited, board.getTimestampTracker().getEdited());
    }

    @Test
    void testIsEditedFalse() {
        assertFalse(board2.getTimestampTracker().isEdited());
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
    void testBoardEqualsDifferentClass() {
        assertFalse(board.equals("A string."));
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
        String boardToString = "Board{"
                + "boardId='" + board.getId() + '\''
                + ", boardName='" + board.getName() + '\''
                + ", boardDescription='" + board.getDescription() + '\''
                + ", locked='" + board.isLocked() + '\''
                + ", " + board.getTimestampTracker().toString() + '\''
                + ", username='" + board.getUsername() + '\''
                + '}';
        assertEquals(board.toString(), boardToString);
    }
}
