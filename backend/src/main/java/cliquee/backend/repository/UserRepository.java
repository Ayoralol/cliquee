package cliquee.backend.repository;

import cliquee.backend.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);

  @Query(
    "SELECT u FROM User u WHERE u.id = :id AND u.id NOT IN (SELECT b.blocked.id FROM Block b WHERE b.blocker.id = :userId) AND u.id NOT IN (SELECT b.blocker.id FROM Block b WHERE b.blocked.id = :userId)"
  )
  Optional<User> findByIdExcludingBlocked(Long id, Long userId);

  @Query(
    "SELECT u FROM User u WHERE u.username = :username AND u.id NOT IN (SELECT b.blocked.id FROM Block b WHERE b.blocker.id = :userId) AND u.id NOT IN (SELECT b.blocker.id FROM Block b WHERE b.blocked.id = :userId)"
  )
  Optional<User> findByUsernameExcludingBlocked(String username, Long userId);

  @Query(
    "SELECT u FROM User u WHERE u.email = :email AND u.id NOT IN (SELECT b.blocked.id FROM Block b WHERE b.blocker.id = :userId) AND u.id NOT IN (SELECT b.blocker.id FROM Block b WHERE b.blocked.id = :userId)"
  )
  Optional<User> findByEmailExcludingBlocked(String email, Long userId);

  @Query(
    "SELECT u FROM User u WHERE u.id NOT IN (SELECT b.blocked.id FROM Block b WHERE b.blocker.id = :userId) AND u.id NOT IN (SELECT b.blocker.id FROM Block b WHERE b.blocked.id = :userId)"
  )
  List<User> findAllExcludingBlocked(Long userId);
}
