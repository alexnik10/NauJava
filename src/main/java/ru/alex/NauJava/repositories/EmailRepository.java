package ru.alex.NauJava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.NauJava.entities.Email;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Long> {
    List<Email> findAllByContactId(Long contactId);
}
