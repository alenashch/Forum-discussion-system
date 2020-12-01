package nl.tudelft.sem.group20.authenticationserver.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AuthRequest {


    @Column(name = "token")
    private transient String token;

    public String getToken() {
        return token;
    }
}
