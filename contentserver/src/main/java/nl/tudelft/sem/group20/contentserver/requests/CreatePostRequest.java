package nl.tudelft.sem.group20.contentserver.requests;

public class CreatePostRequest {

    private String body;

    private long boardThreadId;

    /**
     * Builder for CreatePostRequest.
     *
     * @param body body of request
     * @param boardThreadId id of board thread
     */
    public CreatePostRequest(String body, long boardThreadId) {
        this.body = body;
        this.boardThreadId = boardThreadId;
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
