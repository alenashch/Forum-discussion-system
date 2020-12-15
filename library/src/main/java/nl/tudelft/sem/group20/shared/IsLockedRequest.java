package nl.tudelft.sem.group20.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IsLockedRequest {

    //@Column(name = "id")
    private transient long id;

    //@Column(name = "token")
    private transient String token;

    public IsLockedRequest() {
    }

    public IsLockedRequest(long id, String token) {
        this.id = id;
        this.token = token;
    }

    /**
     * Gets the token.
     *
     * @return the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token.
     *
     * @param token - token to be set.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the id of the board.
     *
     * @return id of the board.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of the board.
     *
     * @param id - the id to be set.
     */
    public void setId(long id) {
        this.id = id;
    }



}
