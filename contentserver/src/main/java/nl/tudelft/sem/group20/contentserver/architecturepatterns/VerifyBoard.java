package nl.tudelft.sem.group20.contentserver.architecturepatterns;

import exceptions.BoardIsLockedException;
import exceptions.BoardNotFoundException;
import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.AuthResponse;
import nl.tudelft.sem.group20.shared.IsLockedResponse;
import nl.tudelft.sem.group20.shared.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class VerifyBoard extends BaseHandler {

    @Autowired
    private transient RestTemplate restTemplate;

    public boolean handle(CheckRequest checkRequest){
        IsLockedResponse response = restTemplate.getForObject("http://board-server/board/checklocked/" + checkRequest.boardId,
                IsLockedResponse.class);

        if (response == null || response.getStatus() == StatusResponse.Status.fail) {
            return false;
        }

        if (response.getStatus() == StatusResponse.Status.success && response.isLocked()) {
            return false;
        }

        return super.handle(checkRequest);
    }

}
