package nl.tudelft.sem.group20.authenticationserver.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.internal.cglib.reflect.$FastClass;
import nl.tudelft.sem.group20.authenticationserver.entities.AuthToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AuthTokenTest {

    private transient AuthToken authToken;
    private transient String token = "token";
    private transient String test = "test";

    @BeforeEach
    void setUp() {
        authToken = new AuthToken(token, false);
    }

    @Test
    void constructorTest() {
        authToken = new AuthToken(token, false);
        assertEquals(token, authToken.getToken());
        assertFalse(authToken.isType());
    }

    @Test
    void getTokenTest() {
        assertEquals(token, authToken.getToken());
    }

    @Test
    void setTokenTest() {
        authToken.setToken(test);
        assertEquals(test, authToken.getToken());
    }

    @Test
    void getTeacherTest() {
        assertFalse(authToken.isType());
    }

    @Test
    void setTeacherTest() {
        authToken.setType(true);
        assertTrue(authToken.isType());
    }

}
