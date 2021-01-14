package exceptions;

public class PermissionException extends Exception {

    private static final long serialVersionUID = 16;

    public PermissionException() {

        super("User not allowed to make these changes");
    }

    public PermissionException(String message) {

        super(message);
    }
}
