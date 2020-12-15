package nl.tudelft.sem.group20.contentserver.requests;

import org.hibernate.dialect.function.DB2SubstringFunction;

public class CreatePostRequest {

    private long creatorId;

    private String body;

    private long boardThreadId;

    public CreatePostRequest(long creatorId, String body, long boardThreadId) {
        this.creatorId = creatorId;
        this.body = body;
        this.boardThreadId = boardThreadId;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
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
