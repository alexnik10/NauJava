package ru.alex.NauJava.customRepositories;

import ru.alex.NauJava.entities.Contact;

import java.util.List;

public interface ContactRepositoryCustom {
    List<Contact> findByFirstNameOrLastName(String firstName, String lastName);

    List<Contact> findContactsByGroupName(String groupName);
}
