package ru.alex.NauJava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.NauJava.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
