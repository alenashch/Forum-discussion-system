package nl.tudelft.sem.group20.boardserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import nl.tudelft.sem.group20.boardserver.entities.ExtendedBaseEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtendedBaseEntityTest {
    transient ExtendedBaseEntity extendedBaseEntity;
    transient ExtendedBaseEntity extendedBaseEntity2;
    transient ExtendedBaseEntity extendedBaseEntityCopy;

    @BeforeEach
    void initialize() {
        extendedBaseEntity = new ExtendedBaseEntity(1, "Chandler", "Chan");
        extendedBaseEntity2 = new ExtendedBaseEntity(2, "Monica", "Mon");

        extendedBaseEntityCopy = new ExtendedBaseEntity(extendedBaseEntity.getId(),
                extendedBaseEntity.getName(), extendedBaseEntity.getUsername());
        extendedBaseEntityCopy.setTimestampTracker(extendedBaseEntity.getTimestampTracker());
    }

    @Test
    public void testSetCreated() {
        extendedBaseEntity.getTimestampTracker().setCreated(null);
        extendedBaseEntity.checkCreationTime(extendedBaseEntity);
        assertEquals(extendedBaseEntity.getTimestampTracker().getCreated(), LocalDateTime.now());
    }


    @Test
    public void testEntityTwoEqual() {
        assertTrue(extendedBaseEntity.equals(extendedBaseEntityCopy));
    }

    @Test
    public void testEntityTwoNotEqual() {
        assertFalse(extendedBaseEntity.equals(extendedBaseEntity2));
    }


    @Test
    void testEntityEqualsDifferentClass() {
        assertFalse(extendedBaseEntity.equals("A string."));
    }


    @Test
    void testDifferentHashCodes() {
        assertNotEquals(extendedBaseEntity.hashCode(), extendedBaseEntity2.hashCode());
    }

    @Test
    void testSameHashCodes() {
        Assertions.assertEquals(extendedBaseEntity.hashCode(), extendedBaseEntityCopy.hashCode());
    }


    @Test
    public void testEntityToString() {
        String entityToString = "BaseEntity{"
                + "id=" + extendedBaseEntity.getId()
                + ", name='" + extendedBaseEntity.getName() + '\''
                + ", username='" + extendedBaseEntity.getUsername() + '\''
                + ", " + extendedBaseEntity.getTimestampTracker().toString()
                + '}';
        Assertions.assertEquals(extendedBaseEntity.toString(), entityToString);
    }

}
