package nl.tudelft.sem.template.repository;

import java.util.Optional;
import nl.tudelft.sem.template.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> getById(long id);
}
