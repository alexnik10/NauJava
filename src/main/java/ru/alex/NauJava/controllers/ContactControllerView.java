package ru.alex.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.repositories.ContactRepository;

@Controller
@RequestMapping("/contacts/view")
public class ContactControllerView {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactControllerView(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping("/list")
    public String contactListView(Model model) {
        Iterable<Contact> contacts = contactRepository.findAll();
        model.addAttribute("contacts", contacts);
        return "contactList";
    }
}