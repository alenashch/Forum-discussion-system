package nl.tudelft.sem.group20.authenticationserver.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import nl.tudelft.sem.group20.authenticationserver.embeddable.StatusResponse;

@Entity
public class AuthToken extends StatusResponse {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private transient long id;


    @Column(name = "token")
    private String token;

    @Column(name = "type")
    private boolean type;


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
    public AuthToken(String token, boolean type) {
        super(Status.success, "Success!");
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(token, authToken.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, token, type);
    }
}
