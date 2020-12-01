package nl.tudelft.sem.group20.authenticationserver.embeddable;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RegisterRequest {
    private String password;
    private String email;
    private String username;

    public RegisterRequest() {};

    /**
     * Non-empty constructer for EmailPassword class.
     *
     * @param password - the password of the user.
     * @param email - the email of the user.
     */
    public RegisterRequest(String password, String email, String username) {
        this.password = password;
        this.email = email;
        this.username = username;
    }

    /**
     * Gets the value of the password.
     *
     * @return returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password to the given value.
     *
     * @param password - password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the value of the email.
     *
     * @return returns the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email to the given value.
     *
     * @param email - email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the value of the username.
     *
     * @return returns the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username to the given value.
     *
     * @param username - username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method that checks to see if two objects are equal or not.
     *
     * @return returns true or false, depending on whether
     *         the objects are equal or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterRequest that = (RegisterRequest) o;
        return Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getUsername(), that.getUsername());
    }

    /**
     *  Method that hashes the user object.
     *
     * @return returns integer of the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getPassword(), getEmail(), getUsername());
    }

    /**
     * Method that returns a readable string from the user object.
     *
     * @return readable string of the user object.
     */
    @Override
    public String toString() {
        return "RegisterRequest{"
                + "password='"
                + password
                + '\''
                + ", email='"
                + email
                + '\''
                + ", username='"
                + username
                + '}';
    }
}
