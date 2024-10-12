package ru.alex.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.repositories.ContactRepository;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    @Transactional
    public boolean deleteContactsByIds(List<Long> contactIds) {
        try {
            //contactRepository.deleteAllById(contactIds);
            for (Long id : contactIds) {
                Contact contact = new Contact();
                contact.setId(id);
                contactRepository.delete(contact);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
