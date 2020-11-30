package nl.tudelft.sem.group20.boardserver.repos;

import java.util.Optional;

import nl.tudelft.sem.group20.boardserver.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> getById(long id);
}
