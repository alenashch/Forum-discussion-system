package nl.tudelft.sem.group20.contentserver.architecturepatterns;

public interface Handler {

    boolean handle(CheckRequest checkRequest);

    void setNext(Handler handler);
}
