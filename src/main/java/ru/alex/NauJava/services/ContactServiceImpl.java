package ru.alex.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.NauJava.dao.ContactRepository;
import ru.alex.NauJava.entities.Contact;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void createContact(String number, String name) {
        Contact contact = new Contact();
        contact.setPhoneNumber(number);
        contact.setName(name);
        contactRepository.create(contact);
    }

    @Override
    public Contact findById(Long id) {
        Contact contact = contactRepository.read(id);
        if (contact == null) {
            throw new RuntimeException("Контакт с ID " + id + " не найден.");
        }
        return contact;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.readAll();
    }

    @Override
    public void deleteById(Long id) {
        contactRepository.delete(id);
    }

    @Override
    public void updateNumber(Long id, String newNumber) {
        Contact contact = contactRepository.read(id);
        if (contact != null) {
            contact.setPhoneNumber(newNumber);
            contactRepository.update(contact);
        } else {
            throw new RuntimeException("Контакт с ID " + id + " не найден для обновления номера.");
        }
    }

    @Override
    public void updateName(Long id, String newName) {
        Contact contact = contactRepository.read(id);
        if (contact != null) {
            contact.setName(newName);
            contactRepository.update(contact);
        } else {
            throw new RuntimeException("Контакт с ID " + id + " не найден для обновления имени.");
        }
    }
}
