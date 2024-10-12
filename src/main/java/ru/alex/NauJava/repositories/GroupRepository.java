package ru.alex.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.alex.NauJava.entities.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {
}
