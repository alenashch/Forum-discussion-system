package nl.tudelft.sem.template.repository;

import java.util.Optional;
import nl.tudelft.sem.template.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> getById(long id);
}
