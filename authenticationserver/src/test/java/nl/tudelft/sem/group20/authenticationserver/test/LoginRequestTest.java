package nl.tudelft.sem.group20.authenticationserver.test;

import nl.tudelft.sem.group20.authenticationserver.embeddable.LoginRequest;
import nl.tudelft.sem.group20.authenticationserver.embeddable.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LoginRequestTest {
    transient LoginRequest loginRequest1;
    transient LoginRequest loginRequest2;
    transient String email1;
    transient String email2;
    transient String password1;
    transient String password2;

    /**
     * Initialises the testing objects.
     */
    @BeforeEach
    void initialize() {
        password1 = "pwd";
        email1 = "test@gmail.com";
        loginRequest1 = new LoginRequest(email1, password1);

        password2 = "pwd2";
        email2 = "frodo@gmail.com";
        loginRequest2 = new LoginRequest(email2, password2);
    }

    /**
     * Tests if the constructor constructs an object properly.
     */
    @Test
    void testNonEmptyConstructor() {
        assertNotNull(loginRequest1);
        assertTrue(loginRequest1.getEmail().equals(email1) && loginRequest1.getPassword().equals(password1));
    }

    /**
     * Tests the getters and setters for the username.
     */
    @Test
    void testGetAndSetEmail() {
        loginRequest1.setEmail("hello");
        assertEquals("hello", loginRequest1.getEmail());
    }

    /**
     * Tests the getters and setters for the password.
     */
    @Test
    void testGetAndSetPassword() {
        loginRequest1.setPassword("frodo");
        assertEquals("frodo", loginRequest1.getPassword());
    }

    /**
     * Tests if the same objects will be equal.
     */
    @Test
    void testEqualsSameObject() {
        assertEquals(loginRequest1, loginRequest1);
    }

    /**
     * Tests if a copy of the object will be equal.
     */
    @Test
    void testEqualsTrue() {
        LoginRequest loginRequest1Copy = new LoginRequest("test@gmail.com", "pwd");
        assertEquals(loginRequest1Copy, loginRequest1);
    }

    /**
     * Tests if different initialized objects are not equal.
     */
    @Test
    void testEqualsFalse() {
        assertFalse(loginRequest1.equals(loginRequest2));
    }

    /**
     * Tests if differten types of objects are not equal.
     */
    @Test
    void testEqualsDifferentClass() {
        assertFalse(loginRequest1.equals("A string."));
    }

    /**
     * Tests if two different objects hae different hashcodes.
     */
    @Test
    void testDifferentHashCodes() {
        assertNotEquals(loginRequest1.hashCode(), loginRequest2.hashCode());
    }

    /**
     * Tests if the same object has the same hashcode.
     */
    @Test
    void testSameHashCodes() {
        assertEquals(loginRequest1.hashCode(), loginRequest1.hashCode());
    }

    /**
     * Tests if the toString method works.
     */
    @Test
    void testToString() {
        String string = "LoginRequest{"
                        + "password='"
                        + password1
                        + '\''
                        + ", email='"
                        + email1
                        + '\''
                        + '}';
        assertEquals(string, loginRequest1.toString());
    }
}
