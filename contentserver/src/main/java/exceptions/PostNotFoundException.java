package exceptions;

public class PostNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 13;

    public PostNotFoundException() {

        super("Given post could not be found");
    }

    public PostNotFoundException(String message) {

        super(message);
    }
}
