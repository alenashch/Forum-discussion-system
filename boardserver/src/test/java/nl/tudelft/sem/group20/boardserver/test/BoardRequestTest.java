package nl.tudelft.sem.group20.boardserver.test;

import nl.tudelft.sem.group20.boardserver.requests.CreateBoardRequest;
import nl.tudelft.sem.group20.boardserver.requests.EditBoardRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardRequestTest {
    transient String demoName;
    transient String demoDescription;
    transient boolean demoLocked;

    transient CreateBoardRequest createBoardRequest;
    transient EditBoardRequest editBoardRequest;

    @BeforeEach
    void initialize() {
        demoName = "Demo board";
        demoDescription = "An example.";
        demoLocked = false;

        createBoardRequest = new CreateBoardRequest(demoName, demoDescription);
        editBoardRequest = new EditBoardRequest(demoName, demoDescription, demoLocked);
    }

    @Test
    void testConstructor() {
        assertNotNull(createBoardRequest);
    }

    @Test
    void testGetAndSetName() {
        createBoardRequest.setName("New name.");
        assertEquals("New name.", createBoardRequest.getName());
    }

    @Test
    void testGetAndSetDescription() {
        createBoardRequest.setDescription("New description.");
        assertEquals("New description.", createBoardRequest.getDescription());
    }

    @Test
    void testGetAndSetLocked() {
        editBoardRequest.setLocked(true);
        assertTrue(editBoardRequest.isLocked());
    }
}
