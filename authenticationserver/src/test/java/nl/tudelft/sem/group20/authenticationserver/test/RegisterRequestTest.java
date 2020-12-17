package nl.tudelft.sem.group20.authenticationserver.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.group20.authenticationserver.embeddable.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class RegisterRequestTest {
    transient RegisterRequest registerRequest1;
    transient RegisterRequest registerRequest2;
    transient RegisterRequest registerRequest3;
    transient String email1;
    transient String email2;
    transient String password1;
    transient String password2;
    transient String username1;
    transient String username2;
    transient boolean type1;
    transient boolean type2;

    /**
     * Initialises the testing objects.
     */
    @BeforeEach
    void initialize() {
        username1 = "Test";
        password1 = "pwd";
        email1 = "test@gmail.com";
        type1 = true;
        registerRequest1 = new RegisterRequest(password1, email1, username1, type1);

        username2 = "Frodo";
        password2 = "pwd2";
        email2 = "frodo@gmail.com";
        type2 = true;
        registerRequest2 = new RegisterRequest(password2, email2, username2, type2);

        registerRequest3 = new RegisterRequest();
    }

    /**
     * Tests the empty constructor.
     */
    @Test
    void testEmptyConstructor() {
        assertNotNull(registerRequest3);
    }

    /**
     * Tests if the constructor constructs an object properly.
     */
    @Test
    void testNonEmptyConstructor() {
        assertNotNull(registerRequest1);
        assertTrue(registerRequest1.getUsername().equals(username1)
                && registerRequest1.getPassword().equals(password1)
                && registerRequest1.getEmail().equals(email1)
                && registerRequest1.getType() == type1);
    }

    /**
     * Tests the getters and setters for the username.
     */
    @Test
    void testGetAndSetUsername() {
        registerRequest1.setUsername("hello");
        assertEquals("hello", registerRequest1.getUsername());
    }

    /**
     * Tests the getters and setters for the password.
     */
    @Test
    void testGetAndSetPassword() {
        registerRequest1.setPassword("frodo");
        assertEquals("frodo", registerRequest1.getPassword());
    }

    /**
     * Tests the getters and setters for the email.
     */
    @Test
    void testGetAndSetEmail() {
        registerRequest1.setEmail("baggins@gmail.com");
        assertEquals("baggins@gmail.com", registerRequest1.getEmail());
    }

    /**
     * Tests the getters and setters for the type.
     */
    @Test
    void testGetAndSetType() {
        registerRequest1.setType(false);
        assertEquals(false, registerRequest1.getType());
    }

    /**
     * Tests if the same objects will be equal.
     */
    @Test
    void testEqualsSameObject() {
        assertTrue(registerRequest1.equals(registerRequest1));
    }

    /**
     * Tests if a copy of the object will be equal.
     */
    @Test
    void testEqualsTrue() {
        RegisterRequest registerRequest1Copy = new RegisterRequest(
                "pwd", "test@gmail.com", "Test", true);
        assertTrue(registerRequest1Copy.equals(registerRequest1));
    }

    /**
     * Tests if different initialized objects are not equal.
     */
    @Test
    void testEqualsFalse() {
        assertFalse(registerRequest1.equals(registerRequest2));
    }

    /**
     * Tests if differten types of objects are not equal.
     */
    @Test
    void testEqualsDifferentClass() {
        assertFalse(registerRequest1.equals("A string."));
    }

    /**
     * Tests if two different objects hae different hashcodes.
     */
    @Test
    void testDifferentHashCodes() {
        assertNotEquals(registerRequest1.hashCode(), registerRequest2.hashCode());
    }

    /**
     * Tests if the same object has the same hashcode.
     */
    @Test
    void testSameHashCodes() {
        assertEquals(registerRequest1.hashCode(), registerRequest1.hashCode());
    }

    /**
     * Tests if the toString method works.
     */
    @Test
    void testToString() {
        String string =
                "RegisterRequest{"
                        + "password='"
                        + "pwd"
                        + '\''
                        + ", email='"
                        + "test@gmail.com"
                        + '\''
                        + ", username='"
                        + "Test"
                        + '\''
                        + ", type='"
                        + "true"
                        + '}';
        assertEquals(string, registerRequest1.toString());
    }
}
