package nl.tudelft.sem.template.repos;

import nl.tudelft.sem.template.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
