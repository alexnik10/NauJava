package ru.alex.NauJava.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.alex.NauJava.entities.Contact;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Long> {
    List<Contact> findByFirstNameOrLastName(String firstName, String lastName);

    @Query("SELECT c FROM Contact c JOIN c.groups g WHERE g.name = :groupName")
    List<Contact> findContactsByGroupName(@Param("groupName") String groupName);
}
