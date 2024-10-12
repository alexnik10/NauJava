package ru.alex.NauJava.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.entities.Group;

import java.util.List;

@SpringBootTest
@Transactional
class ContactRepositoryTest {

    private final ContactRepository contactRepository;
    private final GroupRepository groupRepository;

    @Autowired
    ContactRepositoryTest(ContactRepository contactRepository, GroupRepository groupRepository) {
        this.contactRepository = contactRepository;
        this.groupRepository = groupRepository;
    }

    @Test
    void testFindByFirstNameOrLastName() {
        Contact contact1 = new Contact();
        contact1.setFirstName("Alex");
        contact1.setLastName("Doe");
        contactRepository.save(contact1);

        Contact contact2 = new Contact();
        contact2.setFirstName("John");
        contact2.setLastName("Smith");
        contactRepository.save(contact2);

        List<Contact> foundContacts = contactRepository.findByFirstNameOrLastName("Alex", "Smith");

        Assertions.assertFalse(foundContacts.isEmpty());
        Assertions.assertEquals(foundContacts.size(), 2);
    }

    @Test
    void testFindByFirstNameOrLastName_EmptyResult() {
        Contact contact = new Contact();
        contact.setFirstName("Alex");
        contact.setLastName("Smith");
        contactRepository.save(contact);

        List<Contact> foundContacts = contactRepository.findByFirstNameOrLastName("NonExistent", "Person");

        Assertions.assertTrue(foundContacts.isEmpty());
    }

    @Test
    void testFindContactsByGroupName() {
        Group group = new Group();
        group.setName("Friends");
        groupRepository.save(group);

        Contact contact1 = new Contact();
        contact1.setFirstName("John");
        contact1.setLastName("Doe");
        contact1.setGroups(List.of(group));
        contactRepository.save(contact1);

        Contact contact2 = new Contact();
        contact2.setFirstName("John");
        contact2.setLastName("Smith");
        contactRepository.save(contact2);

        List<Contact> foundContacts = contactRepository.findContactsByGroupName("Friends");

        Assertions.assertFalse(foundContacts.isEmpty());
        Assertions.assertEquals(contact1.getId(), foundContacts.getFirst().getId());
    }

    @Test
    void testFindContactsByGroupName_GroupNotFound() {
        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Smith");
        contactRepository.save(contact);

        List<Contact> foundContacts = contactRepository.findContactsByGroupName("NonExistentGroup");

        Assertions.assertTrue(foundContacts.isEmpty());
    }
}