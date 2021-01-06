package nl.tudelft.sem.group20.contentserver.architecturepatterns;


/**
 * Base Handler which implements the handler.
 * Other handlers implement this base, which enforces
 * the structure of chain of responsibility.
 *
 * Once the last responsibility is handled successfully
 * the last handle() (the one located in the base handler)
 * is called which simply return true (meaning all responsibilities were
 * executed successfully).
 */
public abstract class BaseHandler implements Handler {
    transient Handler next;

    public boolean handle(CheckRequest checkRequest) {
        return next == null || next.handle(checkRequest);
    }

    @Override
    public void setNext(Handler next) {
        this.next = next;
    }
}
