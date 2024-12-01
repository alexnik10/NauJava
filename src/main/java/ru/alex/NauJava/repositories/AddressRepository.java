package ru.alex.NauJava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.NauJava.entities.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByContactId(Long contactId);
}
