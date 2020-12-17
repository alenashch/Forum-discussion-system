package nl.tudelft.sem.group20.boardserver.requests;

public class EditBoardRequest extends CreateBoardRequest {
    private boolean locked;
    private long id;

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
