package nl.tudelft.sem.group20.boardserver.requests;

public class EditBoardRequest extends CreateBoardRequest {
    private boolean locked;
    private long id;

    /**
     * Non-empty constructor for EditBoardRequest class.
     *
     * @param name The name of the board.
     * @param description The description of the board.
     * @param locked Indicates whether the board is locked or not.
     * @param id Unique identifier that is used in the database.
     */
    public EditBoardRequest(String name, String description, boolean locked,  long id) {
        super(name, description);
        this.locked = locked;
        this.id = id;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
