import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BoardThread.class)
public class ThreadTest {

    transient BoardThread demoThread1;
    transient BoardThread demoThread2;
    transient BoardThread demoThread2Copy;

    transient long demoId2;
    transient String demoTitle2;
    transient LocalDateTime demoCreated2;
    transient String demoStatement2;
    transient String threadCreator;
    transient boolean locked;

    @BeforeEach
    void initialize() {
        demoThread1 = new BoardThread();

        demoId2 = 2;
        demoTitle2 = "This is a demo board.";
        demoStatement2 = "This is the question";
        threadCreator = "Bob";
        demoCreated2 = LocalDateTime.now();
        locked = true;

        demoThread2 = new BoardThread(demoId2, demoTitle2, demoStatement2,
            threadCreator, demoCreated2, locked);
        demoThread2Copy = new BoardThread(demoId2, demoThread2.getThreadTitle(),
            demoThread2.getStatement(), demoThread2.getThreadCreator(),
            demoThread2.getCreated(), demoThread2.isLocked());
    }

    @Test
    void testEmptyConstructor() {
        assertNotNull(demoThread1);
    }

    @Test
    void testNonEmptyConstructor() {
        assertNotNull(demoThread2);
        assertTrue(demoThread2.getId() == demoId2
            && demoThread2.getThreadTitle().equals(demoTitle2)
            && demoThread2.getStatement().equals(demoStatement2)
            && demoThread2.getCreated().equals(demoCreated2)
            && demoThread2.getThreadCreator().equals(threadCreator)
            && demoThread2.isLocked() == locked);
    }

    @Test
    void testGetAndSetId() {
        demoThread2.setId(3L);
        assertEquals(3L, demoThread2.getId());
    }

    @Test
    void testGetAndSetTitle() {
        demoThread2.setThreadTitle("wow");
        assertEquals("wow", demoThread2.getThreadTitle());
    }

    @Test
    void testGetAndSetStatement() {
        demoThread2.setStatement("yeah");
        assertEquals("yeah", demoThread2.getStatement());
    }

    @Test
    void testGetAndSetTimeCreated() {
        LocalDateTime newCreated = demoCreated2.plusHours(3);
        demoThread2.setCreated(newCreated);
        assertEquals(newCreated, demoThread2.getCreated());
    }

    @Test
    void testGetAndSetCreated() {
        demoThread2.setThreadCreator("Rob");
        assertEquals("Rob", demoThread2.getThreadCreator());
    }

    @Test
    void testEqualsTrue() {
        assertEquals(demoThread2Copy, demoThread2);
    }

    @Test
    void testToString() {
        String string = "Thread{"
            + "id=" + demoThread2.getId()
            + ", threadTitle='" + demoThread2.getThreadTitle() + '\''
            + ", statement='" + demoThread2.getStatement() + '\''
            + ", threadCreatorId='" + demoThread2.getThreadCreator() + '\''
            + ", created=" + demoThread2.getCreated()
            + ", locked=" + demoThread2.isLocked()
            + '}';

        assertEquals(string, demoThread2.toString());
    }


}
