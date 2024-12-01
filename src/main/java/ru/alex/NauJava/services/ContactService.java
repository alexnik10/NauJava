package ru.alex.NauJava.services;

import ru.alex.NauJava.dto.ContactCreateDTO;
import ru.alex.NauJava.dto.ContactDTO;
import ru.alex.NauJava.dto.ContactUpdateDTO;

import java.util.List;

public interface ContactService {
    ContactDTO createContact(ContactCreateDTO contactCreateDTO, String username);

    List<ContactDTO> getAllContactsByUsername(String username);

    ContactDTO getContactById(Long contactId, String username);

    ContactDTO updateContact(ContactUpdateDTO contactUpdateDTO, String username);

    void deleteContact(Long contactId, String username);
}
