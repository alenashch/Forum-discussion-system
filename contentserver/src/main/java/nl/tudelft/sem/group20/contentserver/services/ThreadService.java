package nl.tudelft.sem.group20.contentserver.services;


import exceptions.AuthorizationFailedException;
import exceptions.BoardIsLockedException;
import exceptions.BoardThreadNotFoundException;
import exceptions.PermissionException;
import java.time.LocalDateTime;
import java.util.List;
import nl.tudelft.sem.group20.classes.Board;
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

    private AuthResponse authenticateUser(String token) {
        AuthResponse authResponse = restTemplate.postForObject(
                "http://authentication-server/user/authenticate",
                new AuthRequest(token), AuthResponse.class);

        if (authResponse == null || authResponse.getStatus() == StatusResponse.Status.fail) {
            throw new AuthorizationFailedException();
        }

        return authResponse;

    }

    private boolean isBoardLocked(long boardId) {
        Board board = restTemplate.getForObject("http://board-server/board/get/" + boardId, Board.class);

        if (board == null) {
            throw new BoardIsLockedException();
        }

        return board.getLocked();

    }


    /**
     * Get all thraeds in database.
     *
     * @return List of BoardThread
     */
    public List<BoardThread> getThreads() {
        return threadRepository.findAll();
    }

    public BoardThread getSingleThread(long id) {
        BoardThread bt = threadRepository.findById(5).orElse(null);
        if (bt == null) throw new BoardThreadNotFoundException();
        return bt;
    }

    /**
     * Creates a Thread in the database.
     *
     * @param token   - token to validate the user
     * @param request - CreateEditBoardThreadRequest with information necessary to create a new
     *                thread.
     * @return -1 if the Thread already exists in the database, or the id of the newly
     *         created thread if creation was successful.
     */
    public long createThread(String token, CreateBoardThreadRequest request) {

        AuthResponse res = authenticateUser(token);
        isBoardLocked(request.getBoardId()); // if board locked new thread cant be created

        /*if (res.getStatus() == StatusResponse.Status.fail) {
            return -1;
        }*/

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
    public boolean updateThread(String token, EditBoardThreadRequest request) {


        BoardThread thread = threadRepository.getById(request.getBoardId()).orElse(null);
        if (thread == null) {
            throw new BoardThreadNotFoundException();
        }

        AuthResponse res = authenticateUser(token);
        if (!res.getUsername().equals(thread.getThreadCreator()) && !res.isType()) {
            throw new PermissionException();
        }

        thread.setThreadTitle(request.getTitle());
        thread.setStatement(request.getStatement());
        thread.setLocked(request.isLocked());

        threadRepository.saveAndFlush(thread);

        return true;
    }


}
