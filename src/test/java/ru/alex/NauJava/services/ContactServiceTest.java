package ru.alex.NauJava.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.repositories.ContactRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class ContactServiceTest {

    private final ContactService contactService;
    private final ContactRepository contactRepository;

    @Autowired
    ContactServiceTest(ContactService contactService, ContactRepository contactRepository) {
        this.contactService = contactService;
        this.contactRepository = contactRepository;
    }

    @Test
    void testDeleteContactsByIds_Success() {
        Contact contact1 = new Contact();
        contact1.setFirstName("John");
        contact1.setLastName("Doe");
        contact1.setBirthday(LocalDate.of(1990, 1, 1));

        Contact contact2 = new Contact();
        contact2.setFirstName("Jane");
        contact2.setLastName("Smith");
        contact2.setBirthday(LocalDate.of(1991, 2, 2));

        contactRepository.save(contact1);
        contactRepository.save(contact2);

        boolean result = contactService.deleteContactsByIds(List.of(contact1.getId(), contact2.getId()));

        Assertions.assertTrue(result);

        Optional<Contact> deletedContact1 = contactRepository.findById(contact1.getId());
        Optional<Contact> deletedContact2 = contactRepository.findById(contact2.getId());
        Assertions.assertTrue(deletedContact1.isEmpty());
        Assertions.assertTrue(deletedContact2.isEmpty());
    }

    @Test
    void testDeleteContactsByIds_Failure() {
        Contact contact1 = new Contact();
        contact1.setFirstName("John");
        contact1.setLastName("Doe");
        contact1.setBirthday(LocalDate.of(1990, 1, 1));

        contactRepository.save(contact1);

        boolean result = contactService.deleteContactsByIds(List.of(contact1.getId(), 9999L));

        Assertions.assertFalse(result);

        Optional<Contact> foundContact1 = contactRepository.findById(contact1.getId());
        Assertions.assertTrue(foundContact1.isPresent());
    }

}