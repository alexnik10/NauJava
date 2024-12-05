package ru.alex.NauJava.controllers.ui;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alex.NauJava.dto.email.EmailCreateDTO;
import ru.alex.NauJava.dto.email.EmailDTO;
import ru.alex.NauJava.dto.email.EmailUpdateDTO;
import ru.alex.NauJava.services.EmailService;

@Controller
@RequestMapping("/email")
public class EmailUIController {

    private final EmailService emailService;

    @Autowired
    public EmailUIController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/add/{contactId}")
    public String showAddEmailForm(@PathVariable Long contactId, Model model) {
        EmailCreateDTO emailCreateDTO = new EmailCreateDTO();
        emailCreateDTO.setContactId(contactId);
        model.addAttribute("email", emailCreateDTO);
        return "add-email";
    }

    @PostMapping("/add")
    public String addEmail(@Valid @ModelAttribute("email") EmailCreateDTO emailCreateDTO,
                           BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "add-email";
        }
        emailService.createEmail(emailCreateDTO, authentication.getName());
        return "redirect:/contacts/" + emailCreateDTO.getContactId();
    }

    @GetMapping("/edit/{id}")
    public String showEditEmailForm(@PathVariable Long id,
                                    @RequestParam Long contactId,
                                    Authentication authentication,
                                    Model model) {
        EmailDTO email = emailService.getEmailById(id, authentication.getName());
        model.addAttribute("email", email);
        model.addAttribute("contactId", contactId);
        return "edit-email";
    }

    @PostMapping("/edit")
    public String editEmail(
            @Valid @ModelAttribute("email") EmailUpdateDTO emailUpdateDTO,
            @RequestParam Long contactId,
            BindingResult bindingResult,
            Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "edit-email";
        }
        emailService.updateEmail(emailUpdateDTO, authentication.getName());
        return "redirect:/contacts/" + contactId;
    }

    @PostMapping("/delete/{id}")
    public String deleteEmail(@PathVariable Long id, @RequestParam Long contactId, Authentication authentication) {
        emailService.deleteEmail(id, authentication.getName());
        return "redirect:/contacts/" + contactId;
    }
}
