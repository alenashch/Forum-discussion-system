package nl.tudelft.sem.group20.authenticationserver.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.group20.shared.StatusResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StatusResponseTest {

    private transient StatusResponse statusRes;
    private transient String message1 = "great success";
    private transient String message2 = "happy";

    @BeforeEach
    void setUp() {
        statusRes = new StatusResponse(StatusResponse.Status.success, message1);
    }

    @Test
    void constructorTest() {
        StatusResponse test = new StatusResponse(StatusResponse.Status.success, message2);
        assertEquals(message2, test.getMessage());
    }

    @Test
    void getStatusTest() {
        assertEquals(StatusResponse.Status.success, statusRes.getStatus());
    }

    @Test
    void getMessageTest() {
        assertEquals(message1, statusRes.getMessage());
    }

    @Test
    void setMessageTest() {
        statusRes.setMessage(message2);
        assertEquals(message2, statusRes.getMessage());
    }

    @Test
    void setStatusTest() {
        statusRes.setStatus(StatusResponse.Status.fail);
        assertEquals(StatusResponse.Status.fail, statusRes.getStatus());
    }

    @Test
    void equalsTest() {
        StatusResponse test = new StatusResponse(StatusResponse.Status.success, message1);
        assertTrue(test.equals(statusRes));
    }

    @Test
    void toStringTest() {
        String res = "StatusResponse{"
                + "status=success"
                + ", message='" + message1 + "\'"
                + '}';
        assertEquals(res, statusRes.toString());
    }
}
