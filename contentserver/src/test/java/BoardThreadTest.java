import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BoardThread.class)
public class BoardThreadTest {
    transient BoardThread boardThread1;

    transient long id;
    transient String threadTitle;
    transient String statement;
    transient String threadCreatorId;
    transient LocalDateTime created;
    transient boolean locked;
    transient long boardId;
    transient BoardThread boardThread2;
    transient BoardThread boardThread3;
    transient BoardThread boardThreadCopy2;

    transient Post demoPost;
    transient Post demoPost2;

    transient String demoName2;
    transient String demoName;
    transient long demoId;
    transient long demoId2;
    transient int demoNumber;
    transient String demoBody;
    transient LocalDateTime demoCreated;

    transient Set<Post> posts;
    transient Set<Post> posts2;

    @BeforeEach
    void initialize() {
        boardThread1 = new BoardThread();

        id = 2;
        threadTitle = "Kittens";
        statement = "Cats are cute";
        threadCreatorId = "us3";
        created = LocalDateTime.now();
        locked = false;
        boardId = 5;
        boardThread2 = new BoardThread(id, threadTitle, statement, threadCreatorId,
                created, locked, 33, false);
        boardThread3 = new BoardThread(threadTitle, statement, threadCreatorId,
                created, locked, boardId);
        boardThreadCopy2 =
            new BoardThread(boardThread2.getId(), boardThread2.getThreadTitle(),
                    boardThread2.getStatement(),
                    boardThread2.getThreadCreator(), boardThread2.getCreatedTime(),
                    boardThread2.isLocked(), boardThread2.getBoardId(),
                    boardThread2.isThreadEdited());

        demoName = "Alice";
        demoName2 = "Ayla";
        demoId = 9;
        demoId2 = 8;
        demoNumber = 5;
        demoBody = "This is a demo post.";
        demoCreated = LocalDateTime.now();
        demoPost = new Post(demoId, demoNumber, demoName, demoBody, null, demoCreated);
        demoPost2 = new Post(demoId2, demoNumber, demoName2, demoBody, null, demoCreated);
        posts = new HashSet<>();
        posts.add(demoPost);
        posts2 = new HashSet<>();
    }

    @Test
    void testEmptyConstructor() {
        assertNotNull(boardThread1);
    }

    @Test
    void testNonEmptyConstructor() {
        assertNotNull(boardThread2);
        assertTrue(boardThread2.getId() == id
                && boardThread2.getThreadTitle().equals(threadTitle)
                && boardThread2.getStatement().equals(statement)
                && boardThread2.getThreadCreator().equals(threadCreatorId)
                && boardThread2.getCreatedTime().equals(created)
                && boardThread2.isLocked() == locked);
    }

    @Test
    void testNonEmptyConstructor2() {
        assertNotNull(boardThread3);
        assertTrue(boardThread3.getThreadTitle().equals(threadTitle)
                && boardThread3.getStatement().equals(statement)
                && boardThread3.getThreadCreator().equals(threadCreatorId)
                && boardThread3.getCreatedTime().equals(created)
                && boardThread3.isLocked() == locked
                && boardThread3.getBoardId() == boardId);
    }

    @Test
    void testGetAndSetId() {
        boardThread2.setId(3);
        assertEquals(3, boardThread2.getId());
    }

    @Test
    void testGetAndSetThreadTitle() {
        boardThread2.setThreadTitle("Dogs are nice too");
        assertEquals("Dogs are nice too", boardThread2.getThreadTitle());
    }

    @Test
    void testGetAndSetStatement() {
        boardThread2.setStatement("Puppies in particular");
        assertEquals("Puppies in particular", boardThread2.getStatement());
    }

    @Test
    void testGetAndSetThreadCreatedId() {
        boardThread2.setThreadCreator("Andy");
        assertEquals("Andy", boardThread2.getThreadCreator());
    }

    @Test
    void testGetAndSetPosts() {
        boardThread2.setPosts(posts);
        assertEquals(posts, boardThread2.getPosts());
    }



    @Test
    void testGetAndSetCreated() {
        LocalDateTime newCreated = LocalDateTime.now();
        boardThread2.setCreatedTime(newCreated);
        assertEquals(newCreated, boardThread2.getCreatedTime());
    }

    @Test
    void testGetAndSetLocked() {
        boardThread2.setLocked(true);
        assertEquals(true, boardThread2.isLocked());
    }

    @Test
    void testGetAndSetBoardId() {
        boardThread2.setBoardId(42);
        assertEquals(42, boardThread2.getBoardId());
    }

    @Test
    void testSetAndGetEditedTimeSuccessful() {
        LocalDateTime validEdited = boardThread2.getCreatedTime().plusHours(3);
        boardThread2.setEditedTime(validEdited);
        assertEquals(validEdited, boardThread2.getEditedTime());
    }

    @Test
    void testGetAndSetEdited() {
        boardThread2.setIsThreadEdited(true);
        assertEquals(true, boardThread2.isThreadEdited());
    }

    @Test
    void testIsEditedTrue() {
        boardThread2.setEditedTime(boardThread2.getCreatedTime().plusHours(3));
        assertEquals(boardThread2.getCreatedTime().plusHours(3), boardThread2.getEditedTime());
    }

    @Test
    void testIsEditedFalse() {
        assertFalse(boardThread2.isThreadEdited());
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(boardThread2, boardThread2);
    }

    @Test
    void testEqualsTrue() {
        assertEquals(boardThreadCopy2, boardThread2);
    }

    @Test
    void testEqualsFalse() {
        assertNotEquals(boardThread2, boardThread1);
    }

    @Test
    void testEqualsDifferentClass() {
        assertNotEquals(boardThread2, "A string.");
    }

    @Test
    void testAddPost() {
        boardThread2.addPost(demoPost);
        assertEquals(posts, boardThread2.getPosts());
    }

    @Test
    void testRemovePost() {
        boardThread2.removePost(demoPost);
        assertEquals(posts2, boardThread2.getPosts());
    }

    @Test
    void testDifferentHashCodes() {
        assertNotEquals(boardThread2.hashCode(), boardThread3.hashCode());
    }

    @Test
    void testSameHashCodes() {
        assertEquals(boardThread2.hashCode(), boardThread2.hashCode());
    }

    /*@Test
    void testToString() {
        String string = "Thread{"
                + "id=" + boardThread2.getId()
                + ", threadTitle='" + boardThread2.getThreadTitle() + '\''
                + ", statement='" + boardThread2.getStatement() + '\''
                + ", threadCreatorId='" + boardThread2.getThreadCreator() + '\''
                + ", created=" + boardThread2.getCreatedTime()
                + ", locked=" + boardThread2.isLocked()
                + '}';

        assertEquals(string, boardThread2.toString());
    }*/

}
