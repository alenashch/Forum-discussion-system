package nl.tudelft.sem.group20.contentserver.architecturepatterns;

import exceptions.AuthorizationFailedException;
import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.AuthResponse;
import nl.tudelft.sem.group20.shared.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class VerifyAuth extends BaseHandler {

    @Autowired
    private transient RestTemplate restTemplate;

    public boolean handle(CheckRequest checkRequest){
        AuthResponse authResponse = restTemplate.postForObject(
                "http://authentication-server/user/authenticate",
                new AuthRequest(checkRequest.token), AuthResponse.class);

        if (authResponse == null || authResponse.getStatus() == StatusResponse.Status.fail) {
            return false;
        }

        return super.handle(checkRequest);
    }

}
