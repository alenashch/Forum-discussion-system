package exceptions;

public class AuthorizationFailedException extends Exception {

    private static final long serialVersionUID = 10;

    public AuthorizationFailedException() {

        super("User could be not authorized");
    }

    public AuthorizationFailedException(String message) {

        super(message);
    }

}
