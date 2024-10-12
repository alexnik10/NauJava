package ru.alex.NauJava.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.NauJava.entities.Contact;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Long> {
    List<Contact> findByFirstNameOrLastName(String firstName, String lastName);

    @Query("SELECT c FROM Contact c JOIN c.groups g WHERE g.name = :groupName")
    List<Contact> findContactsByGroupName(@Param("groupName") String groupName);

    @Modifying
    @Query("DELETE FROM Contact c WHERE c.id IN :ids")
    int deleteContactsByIds(@Param("ids") List<Long> ids);
}
