package nl.tudelft.sem.group20.contentserver.architecturepatterns;

import exceptions.AuthorizationFailedException;

/**
 * Interface that defines the main methods all handlers
 * implement.
 * Handle method is where handlers "solve" their duty
 * (e.g. authenticate someone, make a request, etc.)
 * setNext() method is where the next handler is assigned
 * to continue the chain of responsibilities.
 */
public interface Handler {

    boolean handle(CheckRequest checkRequest) throws AuthorizationFailedException, Exception;

    void setNext(Handler handler);
}
