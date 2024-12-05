package ru.alex.NauJava.services;

import ru.alex.NauJava.dto.email.EmailCreateDTO;
import ru.alex.NauJava.dto.email.EmailDTO;
import ru.alex.NauJava.dto.email.EmailUpdateDTO;

import java.util.List;

public interface EmailService {
    EmailDTO createEmail(EmailCreateDTO emailCreateDTO, String username);

    List<EmailDTO> getAllEmailsByContact(Long contactId, String username);

    EmailDTO getEmailById(Long emailId, String username);

    EmailDTO updateEmail(EmailUpdateDTO emailUpdateDTO, String username);

    void deleteEmail(Long emailId, String username);
}
