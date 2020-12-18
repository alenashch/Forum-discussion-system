package nl.tudelft.sem.group20.authenticationserver.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.group20.authenticationserver.entities.AuthToken;
import nl.tudelft.sem.group20.authenticationserver.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AuthTokenTest {

    private transient AuthToken authToken;
    private transient String token = "token";
    private transient String test = "test";
    private transient AuthToken authToken1;
    private transient String token1 = "token1";
    private transient String test1 = "test1";

    @BeforeEach
    void setUp() {
        authToken = new AuthToken(token, false, "abc");
        authToken1 = new AuthToken(token1, false, "abc1");
    }

    @Test
    void constructorTest() {
        authToken = new AuthToken(token, false, "abc");
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

    @Test
    void testGetAndSetId() {
        authToken.setId(5);
        assertEquals(5, authToken.getId());
    }

    @Test
    void testGetAndSetUsername() {
        authToken.setUsername("test");
        assertEquals("test", authToken.getUsername());
    }

    @Test
    void testEquals() {
        assertNotEquals(authToken, authToken1);
    }

    @Test
    void testEquals2() {
        assertNotEquals(authToken, new User());
    }

    @Test
    void testHashcode() {
        assertEquals(authToken.hashCode(), authToken.hashCode());
    }

}
