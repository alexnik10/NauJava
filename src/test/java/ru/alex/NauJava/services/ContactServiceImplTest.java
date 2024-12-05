package ru.alex.NauJava.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.NauJava.dto.contact.ContactCreateDTO;
import ru.alex.NauJava.dto.contact.ContactUpdateDTO;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.entities.User;
import ru.alex.NauJava.exceptions.ContactNotFoundException;
import ru.alex.NauJava.repositories.ContactRepository;
import ru.alex.NauJava.repositories.UserRepository;
import ru.alex.NauJava.utilities.TestEntityFactory;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ContactServiceImplTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createContact_Success() {
        // Arrange
        User user = TestEntityFactory.createUser("user");
        userRepository.save(user);

        ContactCreateDTO contactCreateDTO = new ContactCreateDTO();
        contactCreateDTO.setFirstName("John");
        contactCreateDTO.setLastName("Doe");
        contactCreateDTO.setMiddleName("Middle");
        contactCreateDTO.setBirthday(LocalDate.of(1990, 1, 1));

        // Act
        var result = contactService.createContact(contactCreateDTO, "user");

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(1, contactRepository.count());
    }

    @Test
    void getContactById_UserMismatch_ThrowsException() {
        // Arrange
        User user1 = TestEntityFactory.createUser("user1");
        userRepository.save(user1);

        User user2 = TestEntityFactory.createUser("user2");
        userRepository.save(user2);

        Contact contact = TestEntityFactory.createContact(user1, "John", "Doe");
        contactRepository.save(contact);

        // Act & Assert
        assertThrows(ContactNotFoundException.class, () ->
                contactService.getContactById(contact.getId(), "user2")
        );
    }

    @Test
    void getAllContactsByUsername_Success() {
        // Arrange
        User user = TestEntityFactory.createUser("user");
        userRepository.save(user);

        Contact contact1 = TestEntityFactory.createContact(user, "John", "Doe");
        contactRepository.save(contact1);

        Contact contact2 = TestEntityFactory.createContact(user, "Jane", "Smith");
        contactRepository.save(contact2);

        // Act
        List<?> contacts = contactService.getAllContactsByUsername("user");

        // Assert
        assertEquals(2, contacts.size());
    }

    @Test
    void updateContact_Success() {
        // Arrange
        User user = TestEntityFactory.createUser("user");
        userRepository.save(user);

        Contact contact = TestEntityFactory.createContact(user, "John", "Doe");
        contactRepository.save(contact);

        ContactUpdateDTO updateDTO = new ContactUpdateDTO();
        updateDTO.setId(contact.getId());
        updateDTO.setFirstName("UpdatedJohn");
        updateDTO.setLastName("UpdatedDoe");

        // Act
        var result = contactService.updateContact(updateDTO, "user");

        // Assert
        assertNotNull(result);
        assertEquals("UpdatedJohn", result.getFirstName());
        assertEquals("UpdatedDoe", result.getLastName());
    }

    @Test
    void updateContact_UserMismatch_ThrowsException() {
        // Arrange
        User user1 = TestEntityFactory.createUser("user1");
        userRepository.save(user1);

        User user2 = TestEntityFactory.createUser("user2");
        userRepository.save(user2);

        Contact contact = TestEntityFactory.createContact(user1, "John", "Doe");
        contactRepository.save(contact);

        ContactUpdateDTO updateDTO = new ContactUpdateDTO();
        updateDTO.setId(contact.getId());
        updateDTO.setFirstName("NewName");
        updateDTO.setLastName("NewLastName");

        // Act & Assert
        assertThrows(ContactNotFoundException.class, () ->
                contactService.updateContact(updateDTO, "user2")
        );
    }

    @Test
    void deleteContact_Success() {
        // Arrange
        User user = TestEntityFactory.createUser("user");
        userRepository.save(user);

        Contact contact = TestEntityFactory.createContact(user, "John", "Doe");
        contactRepository.save(contact);

        // Act
        contactService.deleteContact(contact.getId(), "user");

        // Assert
        assertEquals(0, contactRepository.count());
    }

    @Test
    void deleteContact_UserMismatch_ThrowsException() {
        // Arrange
        User user1 = TestEntityFactory.createUser("user1");
        userRepository.save(user1);

        User user2 = TestEntityFactory.createUser("user2");
        userRepository.save(user2);

        Contact contact = TestEntityFactory.createContact(user1, "John", "Doe");
        contactRepository.save(contact);

        // Act & Assert
        assertThrows(ContactNotFoundException.class, () ->
                contactService.deleteContact(contact.getId(), "user2")
        );
    }
}
