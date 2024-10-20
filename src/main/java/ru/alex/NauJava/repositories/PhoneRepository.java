package ru.alex.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.alex.NauJava.entities.Phone;

@RepositoryRestResource
public interface PhoneRepository extends CrudRepository<Phone, Long> {
}
