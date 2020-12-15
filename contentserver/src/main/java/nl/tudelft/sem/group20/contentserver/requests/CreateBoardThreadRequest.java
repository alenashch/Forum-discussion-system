package nl.tudelft.sem.group20.contentserver.requests;

/**
 * Request used to create a new thread.
 */
public class CreateBoardThreadRequest {

    private String title;

    private String statement;

    private long creatorId;

    /**
     * Constructs a new CreateBoardThreadRequest.
     * @param title title of a new thread as a String.
     * @param statement 
     * @param creatorId
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
