package ru.alex.NauJava.services;

import ru.alex.NauJava.entities.Contact;

public interface ContactService {
    void createContact(String number, String name);

    Contact findById(Long id);

    void deleteById(Long id);

    void updateNumber(Long id, String newNumber);

    void updateName(Long id, String newName);
}
