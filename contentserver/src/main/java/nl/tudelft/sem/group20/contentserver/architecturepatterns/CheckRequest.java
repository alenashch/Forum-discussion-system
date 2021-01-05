package nl.tudelft.sem.group20.contentserver.architecturepatterns;

public class CheckRequest {

    public String token;
    public long boardId;

    public CheckRequest(String token, long boardId){
        this.token = token;
        this.boardId = boardId;
    }
}
