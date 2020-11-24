package nl.tudelft.sem.group20.userserver.repository;

import nl.tudelft.sem.group20.userserver.model.BoardThread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<BoardThread, Integer> {

}
