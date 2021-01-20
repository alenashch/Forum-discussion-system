package nl.tudelft.sem.group20.contentserver.test.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.sem.group20.contentserver.requests.CreatePostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreatePostRequestTest {

    private transient CreatePostRequest request;

    @BeforeEach
    void setUp() {

        request = new CreatePostRequest("body", 123L);
    }

    @Test
    void getBody() {

        assertEquals("body", request.getBody());
    }

    @Test
    void setBody() {

        request.setBody("new body");
        assertEquals("new body", request.getBody());
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