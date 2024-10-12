package ru.alex.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.alex.NauJava.entities.Email;

public interface EmailRepository extends CrudRepository<Email, Long> {
}
