package ru.alex.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.alex.NauJava.entities.Address;

@RepositoryRestResource
public interface AddressRepository extends CrudRepository<Address, Long> {
}
