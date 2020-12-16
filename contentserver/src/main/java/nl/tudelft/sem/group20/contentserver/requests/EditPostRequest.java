package nl.tudelft.sem.group20.contentserver.requests;

public class EditPostRequest extends CreatePostRequest{

    private long postId;

    public EditPostRequest(long postId, long boardThreadId, String body) {

        super(body, boardThreadId);
        this.postId = postId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}