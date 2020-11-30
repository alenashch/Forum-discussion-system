package nl.tudelft.sem.group20.contentserver.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Thread, Integer> {

    Optional<Thread> getById(long id);
}
