package nl.tudelft.sem.group20.contentserver.test;

import java.time.LocalDateTime;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.requests.CreateBoardThreadRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditBoardThreadRequest;


/**
 * A simple class builder to facilitate Content testing.
 */
public class TestBoardThreadBuilder {

    transient long id = 2;
    transient String title = "A Thread";
    transient String statement = "I do things";
    transient String threadCreator = "jayson";
    transient boolean locked = false;
    transient long boardId   = 33;
    public LocalDateTime createTime = LocalDateTime.now();
    transient boolean isEdited = false;


    /**
     * Creates new testing BoardThread.
     *
     * @return the new BoardThread.
     */
    public BoardThread makeBoardThread() {
        //LocalDateTime time = LocalDateTime.now();
        return new BoardThread(id, title, statement,
                threadCreator, createTime, locked, boardId, isEdited);

    }

    /**
     * creates and create request for board.
     *
     * @return create request
     */
    public CreateBoardThreadRequest createBoardThreadRequest() {

        return new CreateBoardThreadRequest(title, statement, boardId);
    }


    /**
     * creates and edit request for board.
     *
     * @return edit request
     */
    public EditBoardThreadRequest editBoardThreadRequest() {

        return new EditBoardThreadRequest(title, statement, boardId, locked, id);
    }

    public long getBoardThreadId() {
        return id;
    }

    public void setBoardThreadNumber(int id) {
        this.id = id;
    }

    public String getCreatorName() {
        return threadCreator;
    }

    public void setCreatorName(String creatorName) {
        this.threadCreator = threadCreator;
    }


    public String getBody() {
        return statement;
    }

    public void setBody(String body) {
        this.statement = statement;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }
}
