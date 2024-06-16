package cliquee.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cliquee.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
