package nl.tudelft.sem.group20.contentserver.services;

import exceptions.AuthorizationFailedException;
import exceptions.BoardIsLockedException;
import exceptions.BoardNotFoundException;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.AuthResponse;
import nl.tudelft.sem.group20.shared.IsLockedResponse;
import nl.tudelft.sem.group20.shared.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public abstract class ContentService {

    @Autowired
    final transient ThreadRepository threadRepository;

    @Autowired
    final transient RestTemplate restTemplate;

    public ContentService(
        ThreadRepository threadRepository, RestTemplate restTemplate) {
        this.threadRepository = threadRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * Authenticates user.
     *
     * @param token String with an authentication token in it.
     * @return String with a username.
     * @throws RuntimeException if the authentication fails.
     */
    public String authenticateUser(String token, boolean type) throws Exception {

        AuthResponse authResponse = restTemplate.postForObject("http://authentication-server/user"
                + "/authenticate",
            new AuthRequest(token), AuthResponse.class);

        if (authResponse == null || authResponse.getStatus() == StatusResponse.Status.fail) {

            throw new AuthorizationFailedException();
        }

        if (type && !authResponse.isType()) {

            throw new AuthorizationFailedException();
        }

        return authResponse.getUsername();
    }

    /**
     * Checks if the given board is locked
     *
     * @param boardId the id of the board.
     * @throws Exception if the board is locked or does not exist.
     */
    public void isBoardLocked(long boardId) throws Exception {
        IsLockedResponse
            response = restTemplate.getForObject("http://board-server/board/checklocked/" + boardId,
            IsLockedResponse.class);

        if (response == null || response.getStatus() == StatusResponse.Status.fail) {

            throw new BoardNotFoundException();
        }

        if (response.getStatus() == StatusResponse.Status.success && response.isLocked()) {

            throw new BoardIsLockedException();
        }
    }
}
