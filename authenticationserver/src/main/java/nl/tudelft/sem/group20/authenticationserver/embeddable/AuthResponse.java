package nl.tudelft.sem.group20.authenticationserver.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AuthResponse extends StatusResponse {

    @Column(name = "type")
    private boolean type;


    public AuthResponse() {
        super(Status.fail, "Token invalid");
    }

    public AuthResponse(boolean type) {
        super(Status.success, "Success!");
        this.type = type;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
