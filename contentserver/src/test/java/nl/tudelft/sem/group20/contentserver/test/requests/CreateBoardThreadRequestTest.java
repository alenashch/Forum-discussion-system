package nl.tudelft.sem.group20.contentserver.test.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.sem.group20.contentserver.requests.CreateBoardThreadRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateBoardThreadRequestTest {

    private transient CreateBoardThreadRequest request;

    @BeforeEach
    void setUp() {

        request = new CreateBoardThreadRequest("title", "statement", 123L);
    }

    @Test
    void getTitle() {

        assertEquals("title", request.getTitle());
    }

    @Test
    void setTitle() {

        request.setTitle("hi");
        assertEquals("hi", request.getTitle());
    }

    @Test
    void getStatement() {

        assertEquals("statement", request.getStatement());
    }

    @Test
    void setStatement() {

        request.setStatement("new");
        assertEquals("new", request.getStatement());
    }

    @Test
    void getBoardId() {

        assertEquals(123L, request.getBoardId());
    }

    @Test
    void setBoardId() {

        request.setBoardId(222L);
        assertEquals(222L, request.getBoardId());
    }
}