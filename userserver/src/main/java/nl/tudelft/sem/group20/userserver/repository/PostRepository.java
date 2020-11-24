package nl.tudelft.sem.group20.userserver.repository;

import java.util.Optional;
import nl.tudelft.sem.group20.userserver.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> getById(long id);
}
