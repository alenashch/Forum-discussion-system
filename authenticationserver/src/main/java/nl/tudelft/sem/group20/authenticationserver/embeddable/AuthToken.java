package nl.tudelft.sem.group20.authenticationserver.embeddable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AuthToken extends StatusResponse {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "token")
    private String token;

    /**
     * Creates a failed login token message.
     *
     */
    public AuthToken() {
        super(Status.fail, "Login failed");
    }

    /**
     * Creates a login token used to manage user sessions.
     *
     * @param token   The token to register
    */
    public AuthToken(String token) {
        super(Status.success, "Success!");
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
