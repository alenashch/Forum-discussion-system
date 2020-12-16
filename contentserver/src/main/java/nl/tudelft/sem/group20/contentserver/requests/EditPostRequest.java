package nl.tudelft.sem.group20.contentserver.requests;

public class EditPostRequest extends CreatePostRequest {

    private long postId;

    /**
     * Edit post request builder.
     *
     * @param postId id of post
     * @param boardThreadId id of board thread
     * @param body body of post
     * @param postCreator id of creator
     */
    public EditPostRequest(long postId, long boardThreadId, String body, String postCreator) {

        super(postCreator, body, boardThreadId);
        this.postId = postId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
