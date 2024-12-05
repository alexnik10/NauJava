package ru.alex.NauJava.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.NauJava.dto.phone.PhoneDTO;
import ru.alex.NauJava.dto.phone.PhoneUpdateDTO;
import ru.alex.NauJava.dto.phone.PhoneCreateDTO;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.entities.Phone;
import ru.alex.NauJava.entities.User;
import ru.alex.NauJava.enums.PhoneType;
import ru.alex.NauJava.exceptions.PhoneNotFoundException;
import ru.alex.NauJava.repositories.ContactRepository;
import ru.alex.NauJava.repositories.PhoneRepository;
import ru.alex.NauJava.repositories.UserRepository;
import ru.alex.NauJava.utilities.TestEntityFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class PhoneServiceImplTest {
    @Autowired
    private PhoneService phoneService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Test
    void createPhone_Success() {
        // Arrange
        User user = TestEntityFactory.createUser("user1");
        userRepository.save(user);

        Contact contact = TestEntityFactory.createContact(user, "John", "Doe");
        contactRepository.save(contact);

        PhoneCreateDTO createDTO = new PhoneCreateDTO();
        createDTO.setContactId(contact.getId());
        createDTO.setNumber("1234567890");
        createDTO.setType(PhoneType.MOBILE);

        // Act
        PhoneDTO phoneDTO = phoneService.createPhone(createDTO, "user1");

        // Assert
        assertNotNull(phoneDTO);
        assertEquals("1234567890", phoneDTO.getNumber());
        assertEquals(PhoneType.MOBILE, phoneDTO.getType());
    }

    @Test
    void getPhoneById_UserMismatch_ThrowsException() {
        // Arrange
        User user1 = TestEntityFactory.createUser("user1");
        userRepository.save(user1);

        User user2 = TestEntityFactory.createUser("user2");
        userRepository.save(user2);

        Contact contact = TestEntityFactory.createContact(user1, "John", "Doe");
        contactRepository.save(contact);

        Phone phone = TestEntityFactory.createPhone(contact, "1234567890", PhoneType.MOBILE);
        phoneRepository.save(phone);

        // Act & Assert
        assertThrows(PhoneNotFoundException.class, () ->
                phoneService.getPhoneById(phone.getId(), "user2")
        );
    }

    @Test
    void getAllPhonesByContact_Success() {
        // Arrange
        User user = TestEntityFactory.createUser("user1");
        userRepository.save(user);

        Contact contact = TestEntityFactory.createContact(user, "John", "Doe");
        contactRepository.save(contact);

        Phone phone1 = TestEntityFactory.createPhone(contact, "1234567890", PhoneType.MOBILE);
        Phone phone2 = TestEntityFactory.createPhone(contact, "0987654321", PhoneType.HOME);
        phoneRepository.saveAll(List.of(phone1, phone2));

        // Act
        List<PhoneDTO> phones = phoneService.getAllPhonesByContact(contact.getId(), "user1");

        // Assert
        assertEquals(2, phones.size());
    }

    @Test
    void updatePhone_Success() {
        // Arrange
        User user = TestEntityFactory.createUser("user1");
        userRepository.save(user);

        Contact contact = TestEntityFactory.createContact(user, "John", "Doe");
        contactRepository.save(contact);

        Phone phone = TestEntityFactory.createPhone(contact, "1234567890", PhoneType.MOBILE);
        phoneRepository.save(phone);

        PhoneUpdateDTO updateDTO = new PhoneUpdateDTO();
        updateDTO.setId(phone.getId());
        updateDTO.setNumber("0987654321");
        updateDTO.setType(PhoneType.HOME);

        // Act
        PhoneDTO updatedPhone = phoneService.updatePhone(updateDTO, "user1");

        // Assert
        assertNotNull(updatedPhone);
        assertEquals("0987654321", updatedPhone.getNumber());
        assertEquals(PhoneType.HOME, updatedPhone.getType());
    }

    @Test
    void updatePhone_UserMismatch_ThrowsException() {
        // Arrange
        User user1 = TestEntityFactory.createUser("user1");
        userRepository.save(user1);

        User user2 = TestEntityFactory.createUser("user2");
        userRepository.save(user2);

        Contact contact = TestEntityFactory.createContact(user1, "John", "Doe");
        contactRepository.save(contact);

        Phone phone = TestEntityFactory.createPhone(contact, "1234567890", PhoneType.MOBILE);
        phoneRepository.save(phone);

        PhoneUpdateDTO updateDTO = new PhoneUpdateDTO();
        updateDTO.setId(phone.getId());
        updateDTO.setNumber("0987654321");
        updateDTO.setType(PhoneType.MOBILE);

        // Act & Assert
        assertThrows(PhoneNotFoundException.class, () ->
                phoneService.updatePhone(updateDTO, "user2")
        );
    }

    @Test
    void deletePhone_Success() {
        // Arrange
        User user = TestEntityFactory.createUser("user1");
        userRepository.save(user);

        Contact contact = TestEntityFactory.createContact(user, "John", "Doe");
        contactRepository.save(contact);

        Phone phone = TestEntityFactory.createPhone(contact, "1234567890", PhoneType.MOBILE);
        phoneRepository.save(phone);

        // Act
        phoneService.deletePhone(phone.getId(), "user1");

        // Assert
        assertFalse(phoneRepository.existsById(phone.getId()));
    }

    @Test
    void deletePhone_UserMismatch_ThrowsException() {
        // Arrange
        User user1 = TestEntityFactory.createUser("user1");
        userRepository.save(user1);

        User user2 = TestEntityFactory.createUser("user2");
        userRepository.save(user2);

        Contact contact = TestEntityFactory.createContact(user1, "John", "Doe");
        contactRepository.save(contact);

        Phone phone = TestEntityFactory.createPhone(contact, "1234567890", PhoneType.MOBILE);
        phoneRepository.save(phone);

        // Act & Assert
        assertThrows(PhoneNotFoundException.class, () ->
                phoneService.deletePhone(phone.getId(), "user2")
        );
    }

}