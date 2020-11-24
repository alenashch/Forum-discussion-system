package nl.tudelft.sem.template.repository;

import nl.tudelft.sem.template.model.BoardThread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<BoardThread, Integer> {

}
