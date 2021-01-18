package nl.tudelft.sem.group20.boardserver.test;

import nl.tudelft.sem.group20.boardserver.embeddable.TimestampTracker;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TimestampTrackerTest {
    transient TimestampTracker demoTracker;
    transient TimestampTracker demoTrackerDifferentHashcode;
    transient LocalDateTime newCreationTime;

    @Test
    public void testSetCreated() {
        demoTracker = new TimestampTracker();
        newCreationTime = demoTracker.getCreated().minusHours(3);
        demoTracker.setCreated(newCreationTime);

        assertEquals(newCreationTime, demoTracker.getCreated());
    }

    @Test
    public void testHashcode() {
        demoTracker = new TimestampTracker();
        demoTrackerDifferentHashcode = new TimestampTracker();
        demoTrackerDifferentHashcode.setCreated(LocalDateTime.now().minusDays(1));

        assertNotEquals(demoTracker.hashCode(), demoTrackerDifferentHashcode.hashCode());
    }

    @Test
    public void testToString() {
        demoTracker = new TimestampTracker();
        String result = "created=" + demoTracker.getCreated()
                + ", edited=" + demoTracker.getEdited();

        assertEquals(result, demoTracker.toString());
    }
}
