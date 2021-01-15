package nl.tudelft.sem.group20.contentserver.architecturepatterns;


/**
 * Handler builder. Lets you assign
 * different handlers by adding them to the chain.
 * The handler builder lets you construct the chain
 * from the base, and subsequently allows to add
 * new handlers by using teh addToChain() function.
 * Once chain has all desired handlers, one can simply
 * use the build() function which returns the base
 * handler.
 * This handler then can be used in a chain of responsibilities.
 */
public class HandlerBuilder {

    transient Handler base;
    transient Handler current;

    /**
     * Adds handler to end of chain.
     *
     * @param handler to add
     * @return the builder
     */
    public HandlerBuilder addToChain(Handler handler) {
        if (current != null) {
            current.setNext(handler);
        } else {
            base = handler;
        }
        current = handler;

        return this;
    }

    /**
     * Builds the actual handler.
     *
     * @return the built handler
     */
    public Handler build() {
        return base;
    }
}
