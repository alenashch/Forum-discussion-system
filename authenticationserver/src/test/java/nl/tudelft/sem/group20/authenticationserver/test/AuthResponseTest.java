package nl.tudelft.sem.group20.authenticationserver.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.group20.shared.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthResponseTest {

    private transient AuthResponse statusRes;

    @BeforeEach
    void setUp() {
        statusRes = new AuthResponse(false, "abc");
    }

    @Test
    void constructorTest() {
        AuthResponse test = new AuthResponse(false, "abc");
        assertFalse(test.isType());
    }

    @Test
    void testType() {
        assertFalse(statusRes.isType());
    }

    @Test
    void testUsername() {
        assertEquals("abc", statusRes.getUsername());
    }
}
