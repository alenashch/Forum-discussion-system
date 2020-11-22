package nl.tudelft.sem.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class UserTest {
    transient User user1;
    transient User user2;
    transient Strign username1;
    transient String password1;
    transient String email1;
    transient boolean type1;

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

    /**
     * Tests if the same objects will be equal.
     */
    @Test
    void testEqualsSameObject() {
        assertTrue(user2.equals(user2));
    }

    /**
     * Tests if a copy of the object will be equal.
     */
    @Test
    void testEqualsTrue() {
        user2Copy = new Post(user2.getUserName(), user2.getPassword(), user2.getEmail(),
                user2.getType());
        assertTrue(user2Copy.equals(user2));
    }

    /**
     * Tests if different initialized objects are not equal.
     */
    @Test
    void testEqualsFalse() {
        assertFalse(user2.equals(user1));
    }

    /**
     * Tests if differten types of objects are not equal.
     */
    @Test
    void testEqualsDifferentClass() {
        assertFalse(user2.equals("A string."));
    }

    /**
     * Tests if two different objects hae different hashcodes.
     */
    @Test
    void testDifferentHashCodes() {
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    /**
     * Tests if the same object has the same hashcode.
     */
    @Test
    void testSameHashCodes() {
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    /**
     * Tests if the toString method works.
     */
    @Test
    void testToString() {
        String string = "User{" +
                "username='" + "Test" + '\'' +
                ", password='" + "pwd" + '\'' +
                ", email='" + "test@gmail.com" + '\'' +
                ", type=" + "false" +
                '}';
        assertEquals(string, user2.toString());
    }


}