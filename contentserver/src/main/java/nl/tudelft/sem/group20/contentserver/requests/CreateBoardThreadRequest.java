package nl.tudelft.sem.group20.contentserver.requests;

public class CreateBoardThreadRequest {

    private String title;

    private String statement;

    private Long boardId;

    /**
     * Board Thread Request.
     *
     * @param title     title
     * @param statement statement
     */
    public CreateBoardThreadRequest(String title, String statement) {

        this.title = title;
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

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

}
