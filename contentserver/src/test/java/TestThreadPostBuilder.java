import java.time.LocalDateTime;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.requests.CreateBoardThreadRequest;
import nl.tudelft.sem.group20.contentserver.requests.CreatePostRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditBoardThreadRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditPostRequest;

/**
 * A simple class builder to facilitate Content testing.
 */
public class TestThreadPostBuilder {

    public int postNumber = 123;
    public String creatorName = "Bob";
    public long postId = 1L;
    public long threadId = 2L;
    public String body = "abc";
    public String title = "title";
    public String statement = "statement";
    public LocalDateTime time = LocalDateTime.now();
    public boolean locked = false;


    /**
     * Class instantiate.
     *
     * @return Post
     */
    public Post createTestPost() {
        LocalDateTime time = LocalDateTime.now();
        return new Post(postId, creatorName, postNumber, body, createTestBoardThread(),
            time);
    }

    public BoardThread createTestBoardThread() {

        return new BoardThread(postId, creatorName, title, statement, time, locked);
    }

    public CreatePostRequest createTestCreatePostRequest() {

        return new CreatePostRequest(body, threadId);
    }

    public EditPostRequest createTestEditPostRequest() {

        return new EditPostRequest(postId, threadId, body);
    }

    public CreateBoardThreadRequest createTestCreateBoardThreadRequest() {

        return new CreateBoardThreadRequest(title, statement);
    }

    public EditBoardThreadRequest createTestEditBoardThreadRequest() {

        return new EditBoardThreadRequest(title, statement, threadId);
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}