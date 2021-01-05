package nl.tudelft.sem.group20.contentserver.architecturepatterns;

import org.springframework.web.client.RestTemplate;

public class CheckRequest {

    public transient String token;
    public transient long boardId;
    public transient RestTemplate restTemplate;

    public CheckRequest(String token, long boardId, RestTemplate restTemplate) {
        this.token = token;
        this.boardId = boardId;
        this.restTemplate = restTemplate;
    }
}
