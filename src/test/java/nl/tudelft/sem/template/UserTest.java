package nl.tudelft.sem.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserTest {
    User user1;
    User user2;
    Strign username1;
    String password1;
    String email1;
    boolean type1;

    /**
     * Initialises the testing objects.
     */
    @BeforeEach
    void initialize() {
        user1 = new User();

        username1 = "Test";
        password1 = "pwd";
        email1 = "test@gmail.com";
        type1 = false;
        user2 = new User(username1, password1, email1, type1);
    }

    /**
     * Tests the empty constructor.
     */
    @Test
    void testEmptyConstructor() {
        assertNotNull(user1);
    }

    /**
     * Tests if the constructor constructs an object properly.
     */
    @Test
    void testNonEmptyConstructor() {
        assertNotNull(user2);
        assertTrue(user2.getUserName().equals(username1)
                && user2.getPassword().equals(password1)
                && user2.getEmail().equals(email1)
                && user2.isType() == type1;
    }

    /**
     * Tests the getters and setters for the username.
     */
    @Test
    void testGetAndSetUsername() {
        user2.setUsername("Pippin");
        assertEquals("Pippin", user2.getUsername());
    }

    /**
     * Tests the getters and setters for the password.
     */
    @Test
    void testGetAndSetUsername() {
        user2.setPassword("Merry");
        assertEquals("Merry", user2.getPassword());
    }

    /**
     * Tests the getters and setters for the email.
     */
    @Test
    void testGetAndSetUsername() {
        user2.setEmail("baggins@gmail.com");
        assertEquals("baggins@gmail.com", user2.getEmail());
    }

    /**
     * Tests the getters and setters for the type.
     */
    @Test
    void testGetAndSetUsername() {
        user2.setType(true);
        assertEquals(true, user2.isType());
    }


}