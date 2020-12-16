package nl.tudelft.sem.group20.contentserver.requests;

public class EditBoardThreadRequest extends CreateBoardThreadRequest {

    private long boardThreadId;


    /**
     * Builder for edit Board Thread Request.
     *
     * @param title title board thread
     * @param statement statement of board thread
     * @param boardThreadId board thread id
     */

    public EditBoardThreadRequest(String title, String statement, long boardThreadId) {

        super(title, statement, boardThreadId);
        this.boardThreadId = boardThreadId;
    }

    public long getBoardThreadId() {
        return boardThreadId;
    }

    public void setBoardThreadId(long boardThreadId) {
        this.boardThreadId = boardThreadId;
    }
}
