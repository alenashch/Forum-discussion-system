package nl.tudelft.sem.group20.contentserver.services;


import exceptions.BoardNotFoundException;
import exceptions.BoardThreadNotFoundException;
import exceptions.PermissionException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.group20.contentserver.architecturepatterns.CheckRequest;
import nl.tudelft.sem.group20.contentserver.architecturepatterns.Handler;
import nl.tudelft.sem.group20.contentserver.architecturepatterns.HandlerBuilder;
import nl.tudelft.sem.group20.contentserver.architecturepatterns.VerifyAuth;
import nl.tudelft.sem.group20.contentserver.architecturepatterns.VerifyBoard;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.contentserver.requests.CreateBoardThreadRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditBoardThreadRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ThreadService extends ContentService {

    /**
     * Constructor of PostService.
     *
     * @param threadRepository - ThreadRepository to use.
     * @param restTemplate     - restTemplate to use.
     */
    public ThreadService(ThreadRepository threadRepository, RestTemplate restTemplate) {
        super(threadRepository, restTemplate);
    }

    /**
     * Get all threads in database.
     *
     * @return List of BoardThread
     */
    public List<BoardThread> getThreads() {
        return threadRepository.findAll();
    }

    /**
     * Gets single thread with the given id.
     *
     * @param id id of the new thread
     * @return the found thread
     */
    public BoardThread getSingleThread(long id) throws Exception {

        return threadRepository.findById(id).orElseThrow(BoardNotFoundException::new);
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
    public long createThread(String token, CreateBoardThreadRequest request) throws Exception {

        //Handler created with builder design patter
        Handler h = new HandlerBuilder()
                .addToChain(new VerifyAuth())
                .addToChain(new VerifyBoard())
                .build();


        //Keep reference of check request to get user back
        CheckRequest checkRequest = new CheckRequest(token, request.getBoardId(), restTemplate);

        //check request is used for the process of handling with chain of responsibilities
        h.handle(checkRequest);

        //to create board we get user back from check request
        BoardThread toCreate = new BoardThread(request.getTitle(), request.getStatement(),
                checkRequest.getUsername(), LocalDateTime.now(), false, request.getBoardId());

        toCreate.setIsThreadEdited(false);

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
    public boolean updateThread(String token, EditBoardThreadRequest request) throws Exception{

        Handler h = new HandlerBuilder()
                .addToChain(new VerifyAuth())
                .build();

        CheckRequest checkRequest =
                new CheckRequest(token, request.getBoardId(), restTemplate);

        h.handle(checkRequest);

        BoardThread thread =
                threadRepository.getById(request.getBoardThreadId())
                        .orElseThrow(BoardThreadNotFoundException::new);

        if (!checkRequest.getUsername().equals(thread.getThreadCreator())) {
            throw new PermissionException();
        }

        thread.setThreadTitle(request.getTitle());
        thread.setStatement(request.getStatement());
        thread.setEditedTime(LocalDateTime.now());

        thread.setIsThreadEdited(true); //save in database that its edited

        threadRepository.saveAndFlush(thread);

        return true;
    }

    /**
     * Locks a thread.
     *
     * @param token String containing authentication token.
     * @param id    long containing the id.
     * @return String with the information if the thread was locked.
     */
    public String lockThread(String token, long id) throws Exception {

        authenticateUser(token, true);

        BoardThread thread =
            threadRepository.getById(id).orElseThrow(BoardThreadNotFoundException::new);

        if (thread.isLocked()) {

            return "Thread with ID " + id + " is already locked";
        }

        thread.setLocked(true);
        threadRepository.saveAndFlush(thread);
        return "Thread with ID " + id + " has been locked";
    }

    /**
     * Unlocks a thread.
     *
     * @param token String containing authentication token.
     * @param id    long containing the id.
     * @return String with the information if the thread was unlocked.
     */
    public String unlockThread(String token, long id) throws Exception {

        authenticateUser(token, true);

        BoardThread thread =
            threadRepository.getById(id).orElseThrow(BoardThreadNotFoundException::new);

        if (!thread.isLocked()) {

            return "Thread on ID " + id + " is already unlocked";
        }

        thread.setLocked(false);
        threadRepository.saveAndFlush(thread);
        return "Thread on ID " + id + " has been unlocked";
    }

    /**
     * Get all threads for a given board.
     *
     * @param boardId - id of a board
     * @return list of threads
     */
    public List<BoardThread> getThreadsPerBoard(long boardId) throws Exception {
        isBoardLocked(boardId);

        List<BoardThread> allThreads = getThreads();
        List<BoardThread> threadsPerBoard = new ArrayList<>();

        for(BoardThread thread : allThreads){
            if(thread.getBoardId() == boardId)
                threadsPerBoard.add(thread);
        }

        return threadsPerBoard;

    }
}
