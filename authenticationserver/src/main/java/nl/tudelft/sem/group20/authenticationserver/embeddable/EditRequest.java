package nl.tudelft.sem.group20.authenticationserver.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import nl.tudelft.sem.group20.authenticationserver.entities.User;
import nl.tudelft.sem.group20.shared.AuthRequest;


@Embeddable
public class EditRequest {


    @Column(name = "authrequest")
    private transient AuthRequest authRequest;


    @Column(name = "user")
    private transient User user;

    public AuthRequest getAuthRequest() {
        return authRequest;
    }

    public User getUser() {
        return user;
    }


}
