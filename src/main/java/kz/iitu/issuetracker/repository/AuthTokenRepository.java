package kz.iitu.issuetracker.repository;

import kz.iitu.issuetracker.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findByToken(String token);
    boolean existsByToken(String token);
    void deleteAllByUsername(String username);
}
