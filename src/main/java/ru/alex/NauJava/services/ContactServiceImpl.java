package ru.alex.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.NauJava.dto.contact.ContactCreateDTO;
import ru.alex.NauJava.dto.contact.ContactDTO;
import ru.alex.NauJava.dto.contact.ContactUpdateDTO;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.entities.User;
import ru.alex.NauJava.exceptions.ContactNotFoundException;
import ru.alex.NauJava.repositories.ContactRepository;
import ru.alex.NauJava.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ContactDTO createContact(ContactCreateDTO contactCreateDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + username));

        Contact contact = new Contact();
        contact.setUser(user);
        contact.setFirstName(contactCreateDTO.getFirstName());
        contact.setLastName(contactCreateDTO.getLastName());
        contact.setMiddleName(contactCreateDTO.getMiddleName());
        contact.setBirthday(contactCreateDTO.getBirthday());
        Contact savedContact = contactRepository.save(contact);
        return mapToDTO(savedContact);
    }

    @Override
    public List<ContactDTO> getAllContactsByUsername(String username) {
        return contactRepository.findAllByUserUsername(username)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDTO getContactById(Long contactId, String username) {
        Contact contact = contactRepository.findById(contactId)
                .filter(c -> c.getUser().getUsername().equals(username))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + contactId));
        return mapToDTO(contact);
    }

    @Override
    public ContactDTO updateContact(ContactUpdateDTO contactUpdateDTO, String username) {
        Contact contact = contactRepository.findById(contactUpdateDTO.getId())
                .filter(c -> c.getUser().getUsername().equals(username))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + contactUpdateDTO.getId()));
        contact.setFirstName(contactUpdateDTO.getFirstName());
        contact.setLastName(contactUpdateDTO.getLastName());
        contact.setMiddleName(contactUpdateDTO.getMiddleName());
        contact.setBirthday(contactUpdateDTO.getBirthday());
        Contact updatedContact = contactRepository.save(contact);
        return mapToDTO(updatedContact);
    }

    @Override
    public void deleteContact(Long contactId, String username) {
        Contact contact = contactRepository.findById(contactId)
                .filter(c -> c.getUser().getUsername().equals(username))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + contactId));
        contactRepository.deleteById(contactId);
    }

    private ContactDTO mapToDTO(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setMiddleName(contact.getMiddleName());
        dto.setBirthday(contact.getBirthday());
        return dto;
    }
}
