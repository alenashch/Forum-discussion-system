package exceptions;

public class BoardThreadNotFoundException extends Exception {

    private static final long serialVersionUID = 11;

    public BoardThreadNotFoundException() {

        super("Given thread could not be found");
    }

    public BoardThreadNotFoundException(String message) {

        super(message);
    }
}
