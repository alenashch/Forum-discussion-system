package nl.tudelft.sem.group20.authenticationserver.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.sem.group20.authenticationserver.embeddable.AuthToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthTokenTest {

    private transient AuthToken authToken;
    private transient String token = "token";
    private transient String test = "test";

    @BeforeEach
    void setUp() {
        authToken = new AuthToken(token);
    }

    @Test
    void constructorTest() {
        authToken = new AuthToken(token);
        assertEquals(token, authToken.getToken());
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

}
