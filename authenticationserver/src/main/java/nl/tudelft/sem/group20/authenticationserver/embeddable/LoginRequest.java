package nl.tudelft.sem.group20.authenticationserver.embeddable;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class LoginRequest {
    private String password;
    private String email;

    /**
     * Non-empty constructer for EmailPassword class.
     *
     * @param password - the password of the user.
     * @param email - the email of the user.
     */
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
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
     * Method that checks to see if two objects are equal or not.
     *
     * @return returns true or false, depending on whether
     *         the objects are equal or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginRequest that = (LoginRequest) o;
        return Objects.equals(getPassword(), that.getPassword())
                && Objects.equals(getEmail(), that.getEmail());
    }

    /**
     *  Method that hashes the user object.
     *
     * @return returns integer of the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getPassword(), getEmail());
    }

    /**
     * Method that returns a readable string from the user object.
     *
     * @return readable string of the user object.
     */
    @Override
    public String toString() {
        return "LoginRequest{"
                + "password='"
                + password
                + '\''
                + ", email='"
                + email
                + '\''
                + '}';
    }
}

