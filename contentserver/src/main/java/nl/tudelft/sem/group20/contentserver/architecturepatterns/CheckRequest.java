package nl.tudelft.sem.group20.contentserver.architecturepatterns;

import org.springframework.web.client.RestTemplate;

public class CheckRequest {

    public transient String token;
    public transient long boardId;
    public transient RestTemplate restTemplate;
    public transient String username;

    public CheckRequest(String token, long boardId, RestTemplate restTemplate) {
        this.token = token;
        this.boardId = boardId;
        this.restTemplate = restTemplate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
