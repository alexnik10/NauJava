package ru.alex.NauJava.services;

import ru.alex.NauJava.dto.contact.ContactCreateDTO;
import ru.alex.NauJava.dto.contact.ContactDTO;
import ru.alex.NauJava.dto.contact.ContactUpdateDTO;

import java.util.List;

public interface ContactService {
    ContactDTO createContact(ContactCreateDTO contactCreateDTO, String username);

    List<ContactDTO> getAllContactsByUsername(String username);

    ContactDTO getContactById(Long contactId, String username);

    ContactDTO updateContact(ContactUpdateDTO contactUpdateDTO, String username);

    void deleteContact(Long contactId, String username);
}
