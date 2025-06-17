package sreenidhi.microfinanceSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import sreenidhi.microfinanceSystem.model.User; // Import your User entity

public interface UserRepository extends JpaRepository<User, Long> {

    // Method to find a user by their username
    Optional<User> findByUsername(String username);

    // Method to find a user by their email
    Optional<User> findByEmail(String email); // <-- Add this line

    // Method to check if a username already exists (useful for registration)
    boolean existsByUsername(String username);

    // Method to check if an email already exists (useful for registration)
    boolean existsByEmail(String email);
}