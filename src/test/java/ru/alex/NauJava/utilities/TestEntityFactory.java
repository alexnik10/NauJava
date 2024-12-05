package ru.alex.NauJava.utilities;

import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.entities.Phone;
import ru.alex.NauJava.entities.User;
import ru.alex.NauJava.enums.PhoneType;

import java.time.LocalDate;

public class TestEntityFactory {

    public static User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash("testPassword");
        return user;
    }

    public static Contact createContact(User user, String firstName, String lastName) {
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setUser(user);
        contact.setBirthday(LocalDate.of(1990, 1, 1));
        return contact;
    }

    public static Phone createPhone(Contact contact, String number, PhoneType type) {
        Phone phone = new Phone();
        phone.setContact(contact);
        phone.setNumber(number);
        phone.setType(type);
        return phone;
    }
}
