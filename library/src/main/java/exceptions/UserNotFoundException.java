package exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {

        super("User with the given name could not be found");
    }

    public UserNotFoundException(String message) {

        super(message);
    }
}
