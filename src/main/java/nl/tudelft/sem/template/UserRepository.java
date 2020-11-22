package nl.tudelft.sem.template.repository;

import nl.tudelft.sem.template.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Post, Long> {
}
