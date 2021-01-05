package nl.tudelft.sem.group20.contentserver.architecturepatterns;

public class HandlerBuilder {

    transient Handler base;
    transient Handler current;

    /**
     * Adds handler to end of chain
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
     * Builds the actual handler
     * @return the built handler
     */
    public Handler build() {
        return base;
    }
}
