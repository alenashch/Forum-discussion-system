package nl.tudelft.sem.group20.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IsLockedResponse extends StatusResponse {
    //@Column(name = "locked")
    private transient boolean locked;

    /**
     * Constructor to return response if the ID of the board is invalid.
     */
    public IsLockedResponse() {
        super(Status.fail, "ID invalid");
    }

    /**
     * Constructor to return response if the ID of the board is valid.
     *
     * @param locked - value of the board.
     */
    public IsLockedResponse(boolean locked) {
        super(Status.success, "Success!");
        this.locked = locked;
    }

    /**
     * Gets the locked value of the board.
     *
     * @return the value of locked.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the value of locked for the board.
     *
     * @param locked - the value locked should be set to.
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
