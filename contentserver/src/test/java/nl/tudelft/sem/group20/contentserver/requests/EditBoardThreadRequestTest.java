package nl.tudelft.sem.group20.contentserver.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EditBoardThreadRequestTest {

    private transient EditBoardThreadRequest request;

    @BeforeEach
    void setUp() {

        request = new EditBoardThreadRequest("title", "statement", 123L,
                false, 123L);
    }

    @Test
    void getBoardThreadId() {

        assertEquals(123L, request.getBoardThreadId());
    }

    @Test
    void setBoardThreadId() {

        request.setBoardThreadId(222L);
        assertEquals(222L, request.getBoardThreadId());
    }
}