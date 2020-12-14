package nl.tudelft.sem.group20.shared.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.IsLockedRequest;
import nl.tudelft.sem.group20.shared.IsLockedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class IsLockedRequestTest {
    transient String token;
    transient long id;
    transient IsLockedRequest isLockedRequest1;
    transient IsLockedRequest isLockedRequest2;

    @BeforeEach
    void setUp() {
        token = "hello!";
        id = 7;
        isLockedRequest1 = new IsLockedRequest();
        isLockedRequest2 = new IsLockedRequest(id, token);
    }

    /**
     * Tests the empty constructor.
     */
    @Test
    void testEmptyConstructor() {
        assertNotNull(isLockedRequest1);
    }

    /**
     * Tests if the constructor constructs an object properly.
     */
    @Test
    void testNonEmptyConstructor() {
        assertNotNull(isLockedRequest2);
        assertTrue(isLockedRequest2.getToken().equals(token)
            && isLockedRequest2.getId() == id);
    }

    /**
     * Tests the getters and setters for the username.
     */
    @Test
    void testGetAndSetToken() {
        isLockedRequest2.setToken("Pippin");
        assertEquals("Pippin", isLockedRequest2.getToken());
    }

    /**
     * Tests the getters and setters for the username.
     */
    @Test
    void testGetAndSetId() {
        isLockedRequest2.setId(3);
        assertEquals(3, isLockedRequest2.getId());
    }
}
