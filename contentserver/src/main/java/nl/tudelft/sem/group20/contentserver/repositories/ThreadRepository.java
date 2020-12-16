package nl.tudelft.sem.group20.contentserver.repositories;

import java.util.Optional;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<BoardThread, Long> {

    Optional<BoardThread> getById(long id);
}
