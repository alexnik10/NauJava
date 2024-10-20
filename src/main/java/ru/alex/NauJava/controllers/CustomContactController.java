package ru.alex.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.NauJava.customRepositories.ContactRepositoryCustom;
import ru.alex.NauJava.entities.Contact;

import java.util.List;

@RestController
@RequestMapping("/custom/contacts")
public class CustomContactController {

    private final ContactRepositoryCustom contactRepositoryCustom;

    @Autowired
    public CustomContactController(ContactRepositoryCustom contactRepositoryCustom) {
        this.contactRepositoryCustom = contactRepositoryCustom;
    }

    @GetMapping("/search")
    public List<Contact> findByFirstNameOrLastName(@RequestParam String firstName,
                                                   @RequestParam String lastName) {
        return contactRepositoryCustom.findByFirstNameOrLastName(firstName, lastName);
    }

    @GetMapping("/group")
    public List<Contact> findContactsByGroupName(@RequestParam String groupName) {
        return contactRepositoryCustom.findContactsByGroupName(groupName);
    }
}