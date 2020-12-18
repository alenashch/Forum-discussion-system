package nl.tudelft.sem.group20.shared;

import javax.persistence.Embeddable;

@Embeddable
public class AuthRequest {

    private transient String token;

    public AuthRequest() {
    }

    public AuthRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
