package exceptions;

public class ThreadIsLockedException extends RuntimeException {

    private static final long serialVersionUID = 14;

    public ThreadIsLockedException() {

        super("Given thread is locked and new items can't be added");
    }

    public ThreadIsLockedException(String message) {

        super(message);
    }
}
