import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BoardThread.class)
public class BoardThreadTest {

    transient BoardThread thread1;
    transient BoardThread thread2;
    transient BoardThread thread2Copy;

    transient long id;
    transient String title;
    transient LocalDateTime createdTime;
    transient String statement;
    transient String threadCreator;
    transient boolean locked;
    transient long boardId;
    transient boolean isEdited;

    @BeforeEach
    void initialize() {
        thread1 = new BoardThread();

        boardId = 3L;
        id = 2;
        title = "This is a demo board.";
        statement = "This is the question";
        threadCreator = "Bob";
        createdTime = LocalDateTime.now();
        locked = true;
        boardId = 3;
        isEdited = false;


        thread2 = new BoardThread(id, title, statement,
            threadCreator, createdTime, locked, boardId, isEdited);

        thread2Copy = new BoardThread(id, thread2.getThreadTitle(),
            thread2.getBody(), thread2.getCreatorName(),
            thread2.getCreatedTime(), thread2.isLocked(),
                thread2.getBoardId(), thread2.isThreadEdited());
    }

    @Test
    void testEmptyConstructor() {
        assertNotNull(thread1);
    }

    @Test
    void testNonEmptyConstructor() {
        assertNotNull(thread2);
        assertTrue(thread2.getId() == id
            && thread2.getThreadTitle().equals(title)
            && thread2.getBody().equals(statement)
            && thread2.getCreatedTime().equals(createdTime)
            && thread2.getCreatorName().equals(threadCreator)
            && thread2.isLocked() == locked);
    }

    @Test
    void testNonEmptyConstructor2() {

        BoardThread demoThread3 = new BoardThread(title, statement,
            threadCreator, createdTime, locked, boardId);
        assertNotNull(thread2);

        assertTrue(thread2.getThreadTitle().equals(title)
            && thread2.getBody().equals(statement)
            && thread2.getCreatedTime().equals(createdTime)
            && thread2.getCreatorName().equals(threadCreator)
            && thread2.isLocked() == locked
            && demoThread3.getBoardId() == boardId);
    }

    @Test
    void testGetAndSetId() {
        thread2.setId(3L);
        assertEquals(3L, thread2.getId());
    }

    @Test
    void testGetAndSetTitle() {
        thread2.setThreadTitle("wow");
        assertEquals("wow", thread2.getThreadTitle());
    }

    @Test
    void testGetAndSetStatement() {
        thread2.setBody("yeah");
        assertEquals("yeah", thread2.getBody());
    }

    @Test
    void testGetAndSetTimeCreated() {
        LocalDateTime newCreated = createdTime.plusHours(3);
        thread2.setCreatedTime(newCreated);
        assertEquals(newCreated, thread2.getCreatedTime());
    }

    @Test
    void testGetAndSetTimeEdted() {
        LocalDateTime newCreated = createdTime.plusHours(5);
        thread2.setEditedTime(newCreated);
        assertEquals(newCreated, thread2.getEditedTime());
    }

    @Test
    void testGetAndSetThreadCreator() {
        thread2.setCreatorName("Rob");
        assertEquals("Rob", thread2.getCreatorName());
    }

    @Test
    void testGetAndSetIsEdited() {
        thread2.setIsThreadEdited(true);
        assertTrue(thread2.isThreadEdited());
    }

    @Test
    void testEqualsTrue() {
        assertEquals(thread2Copy, thread2);
    }

    @Test
    void testAddPost() {
        Post post = new Post();
        thread2.addPost(post);
        assertTrue(thread2.getPosts().contains(post));
    }

    @Test
    void removePost() {
        Post post = new Post();
        thread2.addPost(post);
        thread2.removePost(post);
        assertFalse(thread2.getPosts().contains(post));
    }

    @Test
    void testDifferentHashCodes() {
        assertNotEquals(thread2.hashCode(), thread1.hashCode());
    }

    @Test
    void testSameHashCodes() {
        assertEquals(thread2.hashCode(), thread2.hashCode());
    }

    @Test
    void testToString() {
        String string = "BoardThread{"
                + "id=" + thread2.getId()
                + ", threadTitle='" + thread2.getThreadTitle() + '\''
                + ", statement='" + thread2.getBody() + '\''
                + ", threadCreator='" + thread2.getCreatorName() + '\''
                + ", createdTime=" + thread2.getCreatedTime()
                + ", editedTime=" + thread2.getEditedTime()
                + ", locked=" + thread2.isLocked()
                + ", boardId=" + thread2.getBoardId()
                + ", isEdited=" + thread2.isThreadEdited()
                + ", posts=" + thread2.getPosts()
                + '}';

        assertEquals(string, thread2.toString());
    }


}
