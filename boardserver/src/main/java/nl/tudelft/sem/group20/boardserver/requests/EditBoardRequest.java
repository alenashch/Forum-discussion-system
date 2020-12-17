package nl.tudelft.sem.group20.boardserver.requests;

public class EditBoardRequest extends CreateBoardRequest {
    boolean locked;

    public EditBoardRequest(String name, String description, boolean locked) {
        super(name, description);
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
