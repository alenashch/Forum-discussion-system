package nl.tudelft.sem.group20.contentserver.repositories;

import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<BoardThread, Integer> {

}
