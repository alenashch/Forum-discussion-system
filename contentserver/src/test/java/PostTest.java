import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Post.class)
public class PostTest {
    transient Post demoPost1;

    transient long demoId2;
    transient int demoNumber2;
    transient String demoBody2;
    transient LocalDateTime demoCreated2;
    transient LocalDateTime invalidEdited;
    transient LocalDateTime validEdited;
    transient Post demoPost2;
    transient Post demoPost2Copy;

    @BeforeEach
    void initialize() {
        demoPost1 = new Post();

        demoId2 = 2;
        demoNumber2 = 1;
        demoBody2 = "This is a demo post.";
        demoCreated2 = LocalDateTime.now();
        demoPost2 = new Post(demoId2, demoNumber2, demoBody2, demoCreated2);
        demoPost2Copy = new Post(demoPost2.getId(), demoPost2.getPostNumber(), demoPost2.getBody(),
                demoPost2.getCreated());
    }

    @Test
    void testEmptyConstructor() {
        assertNotNull(demoPost1);
    }

    @Test
    void testNonEmptyConstructor() {
        assertNotNull(demoPost2);
        assertTrue(demoPost2.getId() == demoId2
                && demoPost2.getPostNumber() == demoNumber2
                && demoPost2.getBody().equals(demoBody2)
                && demoPost2.getCreated().equals(demoCreated2)
                && demoPost2.getEdited().equals(demoCreated2));
    }

    @Test
    void testGetAndSetId() {
        demoPost2.setId(3);
        assertEquals(3, demoPost2.getId());
    }

    @Test
    void testGetAndSetPostNumber() {
        demoPost2.setPostNumber(12);
        assertEquals(12, demoPost2.getPostNumber());
    }

    @Test
    void testGetAndSetBody() {
        demoPost2.setBody("Another body.");
        assertEquals("Another body.", demoPost2.getBody());
    }

    @Test
    void testGetAndSetCreated() {
        LocalDateTime newCreated = demoCreated2.plusHours(3);
        demoPost2.setCreated(newCreated);
        assertEquals(newCreated, demoPost2.getCreated());
    }

    /*
    @Test
    void testSetEditedException() {
        invalidEdited = demoPost2.getCreated().minusHours(1);
        assertThrows(IllegalArgumentException.class, () -> demoPost2.setEdited(invalidEdited));
    }

    @Test
    void testSetEditedBoundary() {
        invalidEdited = demoPost2.getCreated();
        assertThrows(IllegalArgumentException.class, () -> demoPost2.setEdited(invalidEdited));
    }
    */
    @Test
    void testSetAndGetEditedSuccessful() {
        validEdited = demoPost2.getCreated().plusHours(3);
        demoPost2.setEdited(validEdited);
        assertEquals(validEdited, demoPost2.getEdited());
    }

    @Test
    void testIsEditedTrue() {
        demoPost2.setEdited(demoPost2.getCreated().plusHours(3));
        assertTrue(demoPost2.isEdited());
    }

    @Test
    void testIsEditedFalse() {
        assertFalse(demoPost2.isEdited());
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(demoPost1, demoPost1);
    }

    @Test
    void testEqualsTrue() {
        assertEquals(demoPost2Copy, demoPost2);
    }

    @Test
    void testEqualsFalse() {
        assertNotEquals(demoPost2, demoPost1);
    }

    @Test
    void testEqualsDifferentClass() {
        assertNotEquals(demoPost2, "A string.");
    }

    @Test
    void testDifferentHashCodes() {
        assertNotEquals(demoPost1.hashCode(), demoPost2.hashCode());
    }

    @Test
    void testSameHashCodes() {
        assertEquals(demoPost2.hashCode(), demoPost2Copy.hashCode());
    }

    @Test
    void testToString() {
        String string = "Post{"
                + "id=" + demoPost2.getId()
                + ", postNumber=" + demoPost2.getPostNumber()
                + ", body='" + demoPost2.getBody() + '\''
                + ", created=" + demoPost2.getCreated()
                + ", edited=" + demoPost2.getEdited()
                + '}';
        assertEquals(string, demoPost2.toString());
    }
}
