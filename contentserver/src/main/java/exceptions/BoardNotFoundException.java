package exceptions;

public class BoardNotFoundException extends Exception {

    private static final long serialVersionUID = 15;

    public BoardNotFoundException() {

        super("Given board could not be found");
    }

    public BoardNotFoundException(String message) {

        super(message);
    }
}
