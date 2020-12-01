package nl.tudelft.sem.group20.authenticationserver.repos;

import java.util.Optional;
import nl.tudelft.sem.group20.authenticationserver.embeddable.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findByToken(String token);
}
