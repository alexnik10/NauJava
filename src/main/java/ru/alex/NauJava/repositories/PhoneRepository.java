package ru.alex.NauJava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.NauJava.entities.Phone;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<Phone> findAllByContactId(Long contactId);
}
