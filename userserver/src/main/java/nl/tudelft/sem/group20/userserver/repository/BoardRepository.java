package nl.tudelft.sem.group20.userserver.repository;


import nl.tudelft.sem.group20.userserver.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
