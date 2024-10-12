package ru.alex.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.alex.NauJava.entities.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
