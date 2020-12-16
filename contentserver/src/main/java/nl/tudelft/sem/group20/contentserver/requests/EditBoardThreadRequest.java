package nl.tudelft.sem.group20.contentserver.requests;

public class EditBoardThreadRequest extends CreateBoardThreadRequest {

    private long boardThreadId;

    public EditBoardThreadRequest(String title, String statement, long boardThreadId) {

        super(title, statement);
        this.boardThreadId = boardThreadId;
    }

    public long getBoardThreadId() {
        return boardThreadId;
    }

    public void setBoardThreadId(long boardThreadId) {
        this.boardThreadId = boardThreadId;
    }
}