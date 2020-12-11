package nl.tudelft.sem.group20.contentserver.services;

import java.time.LocalDateTime;
import java.util.List;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.contentserver.requests.CreateBoardThreadRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditBoardThreadRequest;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {

    private final transient ThreadRepository threadRepository;

    public ThreadService(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public List<BoardThread> getThreads() {
        return threadRepository.findAll();
    }

    /**
     * Creates a Thread in the database.
     *
     * @param request - CreateEditBoardThreadRequest with information necessary to create a new
     *                thread.
     * @return -1 if the Thread already exists in the database, or the id of the newly
     * created thread if creation was successful.
     */
    public long createThread(CreateBoardThreadRequest request) {

        BoardThread toCreate = new BoardThread(request.getTitle(), request.getStatement(),
            request.getCreatorId(), LocalDateTime.now(), false);

        if (threadRepository.getById(toCreate.getId()).isPresent()) {
            return -1;
        }

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
