package nl.tudelft.sem.group20.authenticationserver.repos;

import java.util.Optional;
import nl.tudelft.sem.group20.authenticationserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getById(long id);
}
