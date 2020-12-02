package nl.tudelft.sem.group20.authenticationserver.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.group20.authenticationserver.embeddable.AuthResponse;
import nl.tudelft.sem.group20.authenticationserver.embeddable.StatusResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthResponseTest {

    private transient AuthResponse statusRes;

    @BeforeEach
    void setUp() {
        statusRes = new AuthResponse(false);
    }

    @Test
    void constructorTest() {
        AuthResponse test = new AuthResponse(false);
        assertFalse(test.isType());
    }

    @Test
    void testType() {
        assertFalse(statusRes.isType());
    }
}
