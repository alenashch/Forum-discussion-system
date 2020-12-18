package nl.tudelft.sem.group20.shared.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class AuthRequestTest {
    
    transient String token;
    transient AuthRequest authRequest1;
    transient AuthRequest authRequest2;

    @BeforeEach
    void setUp() {
        token = "hello!";
        authRequest1 = new AuthRequest();
        authRequest2 = new AuthRequest(token);
    }

    /**
     * Tests the empty constructor.
     */
    @Test
    void testEmptyConstructor() {
        assertNotNull(authRequest1);
    }

    /**
     * Tests if the constructor constructs an object properly.
     */
    @Test
    void testNonEmptyConstructor() {
        assertNotNull(authRequest2);
        assertEquals(authRequest2.getToken(), token);
    }

    /**
     * Tests the getters and setters for the username.
     */
    @Test
    void testGetAndSetToken() {
        authRequest2.setToken("Pippin");
        assertEquals("Pippin", authRequest2.getToken());
    }
}
