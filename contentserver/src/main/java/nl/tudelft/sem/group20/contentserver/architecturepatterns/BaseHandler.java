package nl.tudelft.sem.group20.contentserver.architecturepatterns;

public abstract class BaseHandler implements Handler {
    Handler next;

    public boolean handle(CheckRequest checkRequest){
        return next == null || next.handle(checkRequest);
    }

    @Override
    public void setNext(Handler next) {
        this.next = next;
    }
}
