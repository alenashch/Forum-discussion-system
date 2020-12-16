package nl.tudelft.sem.group20.contentserver.requests;

public class EditBoardThreadRequest extends CreateBoardThreadRequest {

    private transient boolean locked;
    private transient long boardThreadId;

    /**
     * Builder for edit Board Thread Request.
     *  @param title title board thread
     * @param statement statement of board thread
     * @param boardId board id
     * @param isLocked if thread is locked or not
     * @param boardThreadId id of the thread
     */

    public EditBoardThreadRequest(String title, String statement, long boardId,
                                  boolean isLocked, long boardThreadId) {

        super(title, statement, boardId);
        this.locked   = isLocked;
        this.boardThreadId = boardThreadId;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public long getBoardThreadId() {
        return this.boardThreadId;
    }

    public void setBoardThreadId(long boardThreadId) {
        this.boardThreadId = boardThreadId;
    }
}
