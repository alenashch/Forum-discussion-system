package nl.tudelft.sem.template.repository;


import nl.tudelft.sem.template.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
