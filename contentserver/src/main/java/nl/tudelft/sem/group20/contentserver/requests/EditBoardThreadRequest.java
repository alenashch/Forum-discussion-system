package nl.tudelft.sem.group20.contentserver.requests;

public class EditBoardThreadRequest extends CreateBoardThreadRequest {

    private long boardThreadId;

    /**
     * Builder for edit Board Thread Request.
     *
     * @param title title board thread
     * @param statement statement of board thread
     * @param creator   id of creator
     * @param boardThreadId board thread id
     */
    public EditBoardThreadRequest(String title, String statement, String creator,
                                long boardThreadId) {

        super(title, statement, creator);
        this.boardThreadId = boardThreadId;
    }

    public long getBoardThreadId() {
        return boardThreadId;
    }

    public void setBoardThreadId(long boardThreadId) {
        this.boardThreadId = boardThreadId;
    }
}
