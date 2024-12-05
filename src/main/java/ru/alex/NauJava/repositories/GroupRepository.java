package ru.alex.NauJava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.entities.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByUserId(Long userId);
    List<Group> findAllByUserUsername(String username);
}
