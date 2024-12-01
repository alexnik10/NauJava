package ru.alex.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.alex.NauJava.dto.ContactCreateDTO;
import ru.alex.NauJava.dto.ContactDTO;
import ru.alex.NauJava.dto.ContactUpdateDTO;
import ru.alex.NauJava.services.ContactService;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/create")
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactCreateDTO contactCreateDTO, Authentication authentication) {
            ContactDTO result = contactService.createContact(contactCreateDTO, authentication.getName());
            return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> GetUserContacts(Authentication authentication) {
        List<ContactDTO> result;
        result = contactService.getAllContactsByUsername(authentication.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactUpdateDTO contactUpdateDTO, Authentication authentication) {
        ContactDTO result = contactService.updateContact(contactUpdateDTO, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id, Authentication authentication) {
        ContactDTO contactDTO = contactService.getContactById(id, authentication.getName());
        return ResponseEntity.ok(contactDTO);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Long> deleteContact(@PathVariable Long id, Authentication authentication) {
        contactService.deleteContact(id, authentication.getName());
        return ResponseEntity.ok(id);
    }
}
