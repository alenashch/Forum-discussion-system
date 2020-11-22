package nl.tudelft.sem.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
    transient Board board;

    transient long id;
    transient String name;
    transient String description;

    @BeforeEach
    void initialize() {
        board = new Board();
        id = (long)1;
        name = "Board 1";
        description = "Description of board 1";
    }

    @Test
    void testEmptyConstructor(){
        assertNotNull(board);
    }

    @Test
    public void testGetAndSetId() {
        board.setId((long)2);
        assertEquals((long)2, board.getId());
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
}
