package nl.tudelft.sem.group20.contentserver.services;

import java.time.LocalDateTime;
import java.util.List;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.contentserver.requests.CreateBoardThreadRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditBoardThreadRequest;
import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.AuthResponse;
import nl.tudelft.sem.group20.shared.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ThreadService {

    @Autowired
    private final transient ThreadRepository threadRepository;

    @Autowired
    private transient RestTemplate restTemplate;

    public ThreadService(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public AuthResponse authenticateUser (String token) {
        try {

            //restTemplate.postForObject();
            return restTemplate.postForObject("http://authentication-server/user/authenticate",
                new AuthRequest(token), AuthResponse.class);

        } catch (Exception e) {
            return null;
        }

    }


    /**
     * Get all thraeds in database.
     *
     * @return List of BoardThread
     */
    public List<BoardThread> getThreads() {
        return threadRepository.findAll();
    }

    /**
     * Creates a Thread in the database.
     *
     *
     * @param token - String containing the authentication token.
     * @param request - CreateEditBoardThreadRequest with information necessary to create a new
     *                thread.
     * @return -1 if the Thread already exists in the database, or the id of the newly
     * created thread if creation was successful.
     */
    public long createThread(String token, CreateBoardThreadRequest request) {

        AuthResponse res = authenticateUser(token);
        if (res.getStatus() == StatusResponse.Status.fail) return -1;

        //check if is locked by communicating with board

        BoardThread toCreate = new BoardThread(request.getTitle(), request.getStatement(),
            res.getUsername(), LocalDateTime.now(), false, request.getBoardId());

        threadRepository.saveAndFlush(toCreate);
        return toCreate.getId();
    }

    /**
     * Updates a Thread in the database.
     *
     * @param request - CreateEditBoardThreadRequest with information necessary to edit an existing
     *                thread.
     * @return false if the thread does not exist in the database, and true otherwise.
     */
    public boolean updateThread(EditBoardThreadRequest request) {

        BoardThread thread = threadRepository.getById(request.getBoardThreadId()).orElse(null);

        if (thread == null) {

            return false;
        }

        thread.setThreadTitle(request.getTitle());
        thread.setStatement(request.getStatement());

        threadRepository.saveAndFlush(thread);
        return true;
    }
}
