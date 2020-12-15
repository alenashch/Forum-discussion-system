package nl.tudelft.sem.group20.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AuthResponse extends StatusResponse {

    //@Column(name = "type")
    private boolean type;

    //@Column(name = "username")
    private String username;


    public AuthResponse() {
        super(Status.fail, "Token invalid");
    }

    /**
     * Constructor for the authresponse.
     *
     * @param type - type of the response.
     * @param username - name of the response.
     */
    public AuthResponse(boolean type, String username) {
        super(Status.success, "Success!");
        this.type = type;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
