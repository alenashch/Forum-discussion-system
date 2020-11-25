package nl.tudelft.sem.group20.userserver;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
//@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;

    private String password;
    private String email;
    private boolean type;

    public User() {}

    /**
     * Non-empty constructer for User class.
     *
     * @param id - unique id for the user.
     * @param username - username of the user.
     * @param password - password of the user.
     * @param email - email of the user.
     * @param  type - type of the user, student or teacher.
     */
    public User(long id, String username, String password, String email, boolean type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    /**
     * Gets the value of the id.
     *
     * @return returns the id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id to the given value.
     *
     * @param id - id of the user.
     */
    public void setId(long id) {
        this.id = id;
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
     * Gets the value of the type.
     *
     * @return returns true or false, depending on whether the user is a teacher or student.
     */
    public boolean isType() {
        return type;
    }

    /**
     * Sets the type to the given value.
     *
     * @param type - true or false, depending on whether the user is a teacher or student.
     */
    public void setType(boolean type) {
        this.type = type;
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
        User user = (User) o;
        return getId() == user.getId()
                && isType() == user.isType()
                && Objects.equals(getUsername(), user.getUsername())
                && Objects.equals(getPassword(), user.getPassword())
                && Objects.equals(getEmail(), user.getEmail());
    }

    /**
     *  Method that hashes the user object.
     *
     * @return returns integer of the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getEmail(), isType());
    }

    /**
     * Method that returns a readable string from the user object.
     *
     * @return readable string of the user object.
     */
    @java.lang.Override
    public java.lang.String toString() {
        return "User{"
                    + "id= "
                    + id
                    + "username='"
                    + username
                    + '\''
                    + ", password='"
                    + password
                    + '\''
                    + ", email='"
                    + email
                    + '\''
                    + ", type="
                    + type
                    + '}';
    }
}


