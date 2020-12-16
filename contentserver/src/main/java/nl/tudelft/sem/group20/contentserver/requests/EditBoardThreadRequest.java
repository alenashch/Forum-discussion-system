package nl.tudelft.sem.group20.contentserver.requests;

public class EditBoardThreadRequest extends CreateBoardThreadRequest {

    private transient boolean locked;

    /**
     * Builder for edit Board Thread Request.
     *
     * @param title title board thread
     * @param statement statement of board thread
     * @param boardId board id
     * @param isLocked if thread is locked or not
     */

    public EditBoardThreadRequest(String title, String statement, long boardId,
                                  boolean isLocked) {

        super(title, statement, boardId);
        this.locked = isLocked;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
