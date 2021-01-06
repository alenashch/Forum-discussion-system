package nl.tudelft.sem.group20.contentserver.architecturepatterns;

import exceptions.AuthorizationFailedException;
import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.AuthResponse;
import nl.tudelft.sem.group20.shared.StatusResponse;

public class VerifyAuth extends BaseHandler {

    /**
     * Verifies authentication
     * @param checkRequest info to check
     * @return if it is verified
     */
    @Override
    public boolean handle(CheckRequest checkRequest) {
        AuthResponse authResponse = checkRequest.restTemplate.postForObject(
                "http://authentication-server/user/authenticate",
                new AuthRequest(checkRequest.token), AuthResponse.class);

        if (authResponse == null || authResponse.getStatus() == StatusResponse.Status.fail) {
            throw new AuthorizationFailedException();
        }

        //we need the username back
        checkRequest.setUsername(authResponse.getUsername());

        return super.handle(checkRequest);
    }

}
