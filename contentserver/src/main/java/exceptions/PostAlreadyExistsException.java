package exceptions;

public class PostAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 12;

    public PostAlreadyExistsException() {

        super("Post with the give ID already exists");
    }

    public PostAlreadyExistsException(String message) {

        super(message);
    }
}
