package cliquee.backend.repository;

import cliquee.backend.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);

  @Query("SELECT u.username FROM User u WHERE u.id = :id")
  Optional<String> findUsernameById(UUID id);

  @Query(
    "SELECT u FROM User u WHERE u.id = :id AND u.id NOT IN (SELECT b.blocked.id FROM Block b WHERE b.blocker.id = :userId) AND u.id NOT IN (SELECT b.blocker.id FROM Block b WHERE b.blocked.id = :userId)"
  )
  Optional<User> findByIdExcludingBlocked(UUID id, UUID userId);

  @Query(
    "SELECT u FROM User u WHERE u.username = :username AND u.id NOT IN (SELECT b.blocked.id FROM Block b WHERE b.blocker.id = :userId) AND u.id NOT IN (SELECT b.blocker.id FROM Block b WHERE b.blocked.id = :userId)"
  )
  Optional<User> findByUsernameExcludingBlocked(String username, UUID userId);

  @Query(
    "SELECT u FROM User u WHERE u.email = :email AND u.id NOT IN (SELECT b.blocked.id FROM Block b WHERE b.blocker.id = :userId) AND u.id NOT IN (SELECT b.blocker.id FROM Block b WHERE b.blocked.id = :userId)"
  )
  Optional<User> findByEmailExcludingBlocked(String email, UUID userId);

  @Query(
    "SELECT u FROM User u WHERE u.id NOT IN (SELECT b.blocked.id FROM Block b WHERE b.blocker.id = :userId) AND u.id NOT IN (SELECT b.blocker.id FROM Block b WHERE b.blocked.id = :userId)"
  )
  List<User> findAllExcludingBlocked(UUID userId);

  @Query(
    "SELECT u FROM User u WHERE (LOWER(u.first_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(u.last_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(u.username) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND u.id NOT IN (SELECT b.blocked.id FROM Block b WHERE b.blocker.id = :userId) AND u.id NOT IN (SELECT b.blocker.id FROM Block b WHERE b.blocked.id = :userId)"
  )
  List<User> searchUsersExcludingBlocked(String searchTerm, UUID userId);
}
