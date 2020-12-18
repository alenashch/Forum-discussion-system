package exceptions;

public class BoardIsLockedException extends RuntimeException {

    private static final long serialVersionUID = 14;

    public BoardIsLockedException() {

        super("Given board is locked and new items can't be added");
    }

    public BoardIsLockedException(String message) {

        super(message);
    }
}
