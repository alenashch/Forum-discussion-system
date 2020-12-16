package nl.tudelft.sem.group20.contentserver.requests;

public class CreatePostRequest {

    private String creator;

    private String body;

    private long boardThreadId;

    /**
     * Builder for CreatePostRequest.
     *
     * @param creator id of creator
     * @param body body of request
     * @param boardThreadId id of board thread
     */
    public CreatePostRequest(String creator, String body, long boardThreadId) {
        this.creator = creator;
        this.body = body;
        this.boardThreadId = boardThreadId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getBoardThreadId() {
        return boardThreadId;
    }

    public void setBoardThreadId(long boardThreadId) {
        this.boardThreadId = boardThreadId;
    }
}
