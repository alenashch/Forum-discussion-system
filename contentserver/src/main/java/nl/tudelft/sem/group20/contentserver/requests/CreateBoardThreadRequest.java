package nl.tudelft.sem.group20.contentserver.requests;

public class CreateBoardThreadRequest {

    private String title;

    private String statement;

    private long creatorId;

    /**
     * Board Thread Request.
     * @param title title
     * @param statement statement
     * @param creatorId creatorId
     */
    public CreateBoardThreadRequest(String title, String statement, long creatorId) {

        this.title = title;
        this.statement = statement;
        this.creatorId = creatorId;
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

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }
}
