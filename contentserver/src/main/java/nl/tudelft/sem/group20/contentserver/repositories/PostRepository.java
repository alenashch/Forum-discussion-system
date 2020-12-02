package nl.tudelft.sem.group20.contentserver.repositories;

import java.util.Optional;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> getById(long id);
}
