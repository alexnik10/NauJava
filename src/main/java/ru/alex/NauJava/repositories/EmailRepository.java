package ru.alex.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.alex.NauJava.entities.Email;

@RepositoryRestResource
public interface EmailRepository extends CrudRepository<Email, Long> {
}
