package nl.tudelft.sem.template;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private String username;

        private String password;
        private String email;
        private boolean type;

        public User() {
        }

        /**
         * Non-empty constructer for User class.
         @param username - username of the user.
         @param password - password of the user.
         @param email - email of the user.
         @param  type - type of the user, student or teacher.
         */
        public Country(String username, String password, String email, boolean type) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.type = type;
        }

    /**
     * Gets the value of the username.
     * @return returns the username.
     */
    public String getUsername() {
            return username;
        }

    /**
     * Sets the username to the given value.
     * @param username - username of the user.
     */
        public void setUsername(String username) {
            this.username = username;
        }

    /**
     * Gets the value of the password.
     * @return returns the password.
     */
        public String getPassword() {
            return password;
        }

    /**
     * Sets the password to the given value.
     * @param password - password of the user.
     */
        public void setPassword(String password) {
            this.password = password;
        }

    /**
     * Gets the value of the email.
     * @return returns the email.
     */
        public String getEmail() {
            return email;
        }

    /**
     * Sets the email to the given value.
     * @param email - email of the user.
     */
        public void setEmail(String email) {
            this.email = email;
        }

    /**
     * Gets the value of the type.
     * @return returns true or false, depending on whether the user is a teacher or student.
     */
        public boolean isType() {
            return type;
        }

    /**
     * Sets the type to the given value.
     * @param type - true or false, depending on whether the user is a teacher or student.
     */
        public void setType(boolean type) {
            this.type = type;
        }

    /**
     * Method that checks to see if two objects are equal or not.
     * @param object - object to be compared.
     * @return returns true or false, depending on whether the objects are equal or not.
     */
    public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            if (!super.equals(object)) return false;
            User user = (User) object;
            return type == user.type &&
                    java.util.Objects.equals(username, user.username) &&
                    java.util.Objects.equals(password, user.password) &&
                    java.util.Objects.equals(email, user.email);
        }

    /**
     *  Method that hashes the user object.
     * @return returns integer of the hash code.
     */
    public int hashCode() {
            return Objects.hash(super.hashCode(), username, password, email, type);
        }

    /**
     * Method that returns a readable string from the user object.
     * @return readable string of the user object.
     */
    @java.lang.Override
        public java.lang.String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", email='" + email + '\'' +
                    ", type=" + type +
                    '}';
        }
}


