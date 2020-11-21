package nl.tudelft.sem.template.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {
    Post demoPost1;

    long demoId2;
    int demoNumber2;
    String demoBody2;
    LocalDateTime demoCreated2;
    Post demoPost2;

    @BeforeEach
    void initialize() {
        demoPost1 = new Post();

        demoId2 = 2;
        demoNumber2 = 1;
        demoBody2 = "This is a demo post.";
        demoCreated2 = LocalDateTime.now();
        demoPost2 = new Post(demoId2, demoNumber2, demoBody2, demoCreated2);
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

    @Test
    void testSetEditedException() {
        LocalDateTime invalidEdited = demoPost2.getCreated().minusHours(1);
        assertThrows(IllegalArgumentException.class, () -> demoPost2.setEdited(invalidEdited));
    }

    @Test
    void testSetEditedBoundary() {
        LocalDateTime invalidEdited = demoPost2.getCreated();
        assertThrows(IllegalArgumentException.class, () -> demoPost2.setEdited(invalidEdited));
    }

    @Test
    void testSetAndGetEditedSuccessful() {
        LocalDateTime validEdited = demoPost2.getCreated().plusHours(3);
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
}
