package nl.tudelft.sem.template;

import nl.tudelft.sem.template.entities.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    transient Board board;

    transient long id;
    transient String name;
    transient String description;

    @BeforeEach
    void initialize() {
        board = new Board();
        id = 1;
        name = "Board 1";
        description = "Description of board 1";
    }

    @Test
    void testEmptyConstructor(){
        assertNotNull(board);
    }


    @Test
    public void testGetAndSetId() {
        board.setId(2);
        assertEquals(2, board.getId());
    }


    @Test
    public void testGetAndSetName() {
        board.setName("New Board 1");
        assertEquals("New Board 1", board.getName());
    }

    @Test
    public void testGetAndSetDescription() {
        board.setDescription("New description of board 1");
        assertEquals("New description of board 1", board.getDescription());
    }

    @Test
    public void testBoardTwoEqual() {
        Board board1 = new Board(10,"board 10","description");
        Board board2 = new Board(10,"board 10","description");
        assertTrue(board1.equals(board2));
    }


    @Test
    public void testBoardToString() {
        String boardToString = "Board{boardId='" + board.getId() +
                "', boardName='" + board.getName() +
                "', boardDescription='" + board.getDescription() +
                "'}";
        assertEquals(board.toString(),boardToString);
    }
}
