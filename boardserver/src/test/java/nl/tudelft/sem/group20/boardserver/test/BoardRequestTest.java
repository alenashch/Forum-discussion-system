package nl.tudelft.sem.group20.boardserver.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.group20.boardserver.requests.CreateBoardRequest;
import nl.tudelft.sem.group20.boardserver.requests.EditBoardRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardRequestTest {
    transient String demoName;
    transient String demoDescription;
    transient boolean demoLocked;
    transient long demoId;

    transient CreateBoardRequest createBoardRequest;
    transient EditBoardRequest editBoardRequest;

    @BeforeEach
    void initialize() {
        demoName = "Demo board";
        demoDescription = "An example.";
        demoLocked = false;
        demoId = 1L;

        createBoardRequest = new CreateBoardRequest(demoName, demoDescription);
        editBoardRequest = new EditBoardRequest(demoName, demoDescription, demoLocked, demoId);
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

    @Test
    void testGetAndSetId() {
        editBoardRequest.setId(2);
        assertEquals(2, editBoardRequest.getId());
    }
}
