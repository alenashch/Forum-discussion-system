package nl.tudelft.sem.template.repository;

import java.util.Optional;
import nl.tudelft.sem.template.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getById(long id);
}
