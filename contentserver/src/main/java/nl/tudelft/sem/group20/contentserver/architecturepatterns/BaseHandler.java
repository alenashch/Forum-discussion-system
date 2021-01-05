package nl.tudelft.sem.group20.contentserver.architecturepatterns;

import org.springframework.stereotype.Service;

public abstract class BaseHandler implements Handler {
    transient Handler next;

    public boolean handle(CheckRequest checkRequest) {
        if(next == null){
            return true;
        }else{
            return next.handle(checkRequest);
        }
    }

    @Override
    public void setNext(Handler next) {
        this.next = next;
    }
}
