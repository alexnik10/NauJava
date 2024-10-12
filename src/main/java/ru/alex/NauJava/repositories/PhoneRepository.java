package ru.alex.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.alex.NauJava.entities.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
}
