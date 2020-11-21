package nl.tudelft.sem.template.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    
}
