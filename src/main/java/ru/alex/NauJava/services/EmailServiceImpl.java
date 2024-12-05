package ru.alex.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.NauJava.dto.email.EmailCreateDTO;
import ru.alex.NauJava.dto.email.EmailDTO;
import ru.alex.NauJava.dto.email.EmailUpdateDTO;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.entities.Email;
import ru.alex.NauJava.exceptions.ContactNotFoundException;
import ru.alex.NauJava.exceptions.EmailNotFoundException;
import ru.alex.NauJava.repositories.ContactRepository;
import ru.alex.NauJava.repositories.EmailRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public EmailServiceImpl(EmailRepository emailRepository, ContactRepository contactRepository) {
        this.emailRepository = emailRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public EmailDTO createEmail(EmailCreateDTO emailCreateDTO, String username) {
        Contact contact = contactRepository.findById(emailCreateDTO.getContactId())
                .filter(c -> c.getUser().getUsername().equals(username))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + emailCreateDTO.getContactId()));

        Email email = new Email();
        email.setContact(contact);
        email.setEmailAddress(emailCreateDTO.getEmailAddress());
        email.setType(emailCreateDTO.getType());
        Email savedEmail = emailRepository.save(email);
        return mapToDTO(savedEmail);
    }

    @Override
    public List<EmailDTO> getAllEmailsByContact(Long contactId, String username) {
        Contact contact = contactRepository.findById(contactId)
                .filter(c -> c.getUser().getUsername().equals(username))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + contactId));

        return emailRepository.findAllByContactId(contactId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmailDTO getEmailById(Long emailId, String username) {
        Email email = emailRepository.findById(emailId)
                .filter(e -> e.getContact().getUser().getUsername().equals(username))
                .orElseThrow(() -> new EmailNotFoundException("Email not found with ID: " + emailId));
        return mapToDTO(email);
    }

    @Override
    public EmailDTO updateEmail(EmailUpdateDTO emailUpdateDTO, String username) {
        Email email = emailRepository.findById(emailUpdateDTO.getId())
                .filter(e -> e.getContact().getUser().getUsername().equals(username))
                .orElseThrow(() -> new EmailNotFoundException("Email not found with ID: " + emailUpdateDTO.getId()));

        email.setEmailAddress(emailUpdateDTO.getEmailAddress());
        email.setType(emailUpdateDTO.getType());
        Email updatedEmail = emailRepository.save(email);
        return mapToDTO(updatedEmail);
    }

    @Override
    public void deleteEmail(Long emailId, String username) {
        Email email = emailRepository.findById(emailId)
                .filter(e -> e.getContact().getUser().getUsername().equals(username))
                .orElseThrow(() -> new EmailNotFoundException("Email not found with ID: " + emailId));
        emailRepository.deleteById(emailId);
    }

    private EmailDTO mapToDTO(Email email) {
        EmailDTO dto = new EmailDTO();
        dto.setId(email.getId());
        dto.setEmailAddress(email.getEmailAddress());
        dto.setType(email.getType());
        return dto;
    }
}
