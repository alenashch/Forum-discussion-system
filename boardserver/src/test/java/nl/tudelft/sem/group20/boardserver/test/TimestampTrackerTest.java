package nl.tudelft.sem.group20.boardserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDateTime;
import nl.tudelft.sem.group20.boardserver.embeddable.TimestampTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimestampTrackerTest {
    transient TimestampTracker demoTracker;
    transient TimestampTracker differentDemoTracker;
    transient TimestampTracker demoTrackerCopy;
    transient Object object;
    transient LocalDateTime newCreationTime;

    @BeforeEach
    void initialize() {
        demoTracker = new TimestampTracker();

        differentDemoTracker = new TimestampTracker();
        differentDemoTracker.setCreated(LocalDateTime.now().minusDays(1));

        demoTrackerCopy = new TimestampTracker();
        demoTrackerCopy.setCreated(demoTracker.getCreated());
        demoTrackerCopy.setEdited(demoTracker.getEdited());
    }

    @Test
    void testSetCreated() {
        newCreationTime = demoTracker.getCreated().minusHours(3);
        demoTracker.setCreated(newCreationTime);

        assertEquals(newCreationTime, demoTracker.getCreated());
    }

    @Test
    void testHashcode() {
        demoTracker = new TimestampTracker();

        assertNotEquals(demoTracker.hashCode(), differentDemoTracker.hashCode());
    }

    @Test
    void testToString() {
        String result = "created=" + demoTracker.getCreated()
                + ", edited=" + demoTracker.getEdited();

        assertEquals(result, demoTracker.toString());
    }

    @Test
    void testEqualsDifferentClass() {
        object = new Object();
        assertNotEquals(object, demoTracker);
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(demoTracker, demoTracker);
    }

    @Test
    void testEqualsFalse() {
        assertNotEquals(demoTracker, differentDemoTracker);
    }

    @Test
    void testEqualsTrue() {
        assertEquals(demoTracker, demoTrackerCopy);
    }
}
