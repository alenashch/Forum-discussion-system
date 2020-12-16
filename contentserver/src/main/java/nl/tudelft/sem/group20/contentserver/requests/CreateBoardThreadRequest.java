package nl.tudelft.sem.group20.contentserver.requests;

public class CreateBoardThreadRequest {

    private String title;

    private String statement;

    private String creator;

    private Long boardId;

    /**
     * Board Thread Request.
     *  @param title title
     * @param statement statement
     * @param creator creatorUsername
     */
    public CreateBoardThreadRequest(String title, String statement, String creator) {

        this.title = title;
        this.statement = statement;
        this.creator = creator;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

}
