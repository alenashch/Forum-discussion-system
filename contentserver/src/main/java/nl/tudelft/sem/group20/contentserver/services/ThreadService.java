package nl.tudelft.sem.group20.contentserver.services;

import java.util.List;

import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
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
     * @param toCreate - the Thread to be added.
     * @return -1 if the Thread already exists in the database, or the id of the newly
     *      created thread if creation was successful.
     */
    public long createThread(BoardThread toCreate) {
        if (threadRepository.getById(toCreate.getId()).isPresent()) {
            return -1;
        }

        threadRepository.saveAndFlush(toCreate);
        return toCreate.getId();
    }

    /**
     * Updates a Thread in the database.
     *
     * @param toUpdate - the thread to be updated.
     * @return false if the thread does not exist in the database, and true otherwise.
     */
    public boolean updateThread(BoardThread toUpdate) {
        if (threadRepository.getById(toUpdate.getId()).isEmpty()) {
            return false;
        }

        threadRepository.saveAndFlush(toUpdate);
        return true;
    }
}
