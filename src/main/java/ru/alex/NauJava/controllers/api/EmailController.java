package ru.alex.NauJava.controllers.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.alex.NauJava.dto.email.EmailCreateDTO;
import ru.alex.NauJava.dto.email.EmailDTO;
import ru.alex.NauJava.dto.email.EmailUpdateDTO;
import ru.alex.NauJava.services.EmailService;

import java.util.List;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/create")
    public ResponseEntity<EmailDTO> createEmail(@Valid @RequestBody EmailCreateDTO emailCreateDTO, Authentication authentication) {
        EmailDTO result = emailService.createEmail(emailCreateDTO, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/contact/{contactId}")
    public ResponseEntity<List<EmailDTO>> GetEmails(Long contactId, Authentication authentication) {
        List<EmailDTO> result = emailService.getAllEmailsByContact(contactId, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<EmailDTO> updateEmail(@Valid @RequestBody EmailUpdateDTO emailUpdateDTO, Authentication authentication) {
        EmailDTO result = emailService.updateEmail(emailUpdateDTO, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailDTO> getEmailById(@PathVariable Long id, Authentication authentication) {
        EmailDTO result = emailService.getEmailById(id, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Long> deleteEmail(@PathVariable Long id, Authentication authentication) {
        emailService.deleteEmail(id, authentication.getName());
        return ResponseEntity.ok(id);
    }
}
