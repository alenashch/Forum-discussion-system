package nl.tudelft.sem.group20.contentserver.test.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.sem.group20.contentserver.requests.EditPostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EditPostRequestTest {

    private transient EditPostRequest request;

    @BeforeEach
    void setUp() {

        request = new EditPostRequest(123L, 444L, "body");
    }

    @Test
    void getPostId() {

        assertEquals(123L, request.getPostId());
    }

    @Test
    void setPostId() {

        request.setPostId(333L);
        assertEquals(333L, request.getPostId());
    }
}