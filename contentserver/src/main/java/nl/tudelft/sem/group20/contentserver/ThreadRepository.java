package nl.tudelft.sem.group20.contentserver;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<BoardThread, Integer> {

}
