package ru.alex.NauJava.controllers.ui;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import ru.alex.NauJava.dto.contact.ContactDTO;
import ru.alex.NauJava.dto.address.AddressDTO;
import ru.alex.NauJava.dto.contact.ContactCreateDTO;
import ru.alex.NauJava.dto.contact.ContactUpdateDTO;
import ru.alex.NauJava.dto.email.EmailDTO;
import ru.alex.NauJava.dto.phone.PhoneDTO;
import ru.alex.NauJava.services.AddressService;
import ru.alex.NauJava.services.ContactService;
import ru.alex.NauJava.services.EmailService;
import ru.alex.NauJava.services.PhoneService;

import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactUIController {

    private final ContactService contactService;
    private final PhoneService phoneService;
    private final EmailService emailService;
    private final AddressService addressService;

    @Autowired
    public ContactUIController(ContactService contactService,
                               PhoneService phoneService,
                               EmailService emailService,
                               AddressService addressService) {
        this.contactService = contactService;
        this.phoneService = phoneService;
        this.emailService = emailService;
        this.addressService = addressService;
    }

    @GetMapping
    public String showContactList(Model model, Authentication authentication) {
        List<ContactDTO> contacts = contactService.getAllContactsByUsername(authentication.getName());
        model.addAttribute("contacts", contacts);
        return "contact-list";
    }

    @GetMapping("/{id}")
    public String getContactDetails(@PathVariable Long id, Authentication authentication, Model model) {
        ContactDTO contact = contactService.getContactById(id, authentication.getName());

        List<PhoneDTO> phones = phoneService.getAllPhonesByContact(id, authentication.getName());
        List<EmailDTO> emails = emailService.getAllEmailsByContact(id, authentication.getName());
        List<AddressDTO> addresses = addressService.getAllAddressesByContact(id, authentication.getName());

        model.addAttribute("contact", contact);
        model.addAttribute("phones", phones);
        model.addAttribute("emails", emails);
        model.addAttribute("addresses", addresses);

        return "contact-details";
    }

    @GetMapping("/edit/{id}")
    public String showEditContactForm(@PathVariable Long id, Authentication authentication, Model model) {
        ContactDTO contact = contactService.getContactById(id, authentication.getName());
        model.addAttribute("contact", contact);
        return "edit-contact";
    }

    @PostMapping("/edit")
    public String editContact(
            @Valid @ModelAttribute("contact") ContactUpdateDTO contactUpdateDTO,
            BindingResult bindingResult,
            Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "edit-contact";
        }
        contactService.updateContact(contactUpdateDTO, authentication.getName());
        return "redirect:/contacts/" + contactUpdateDTO.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id, Authentication authentication) {
        contactService.deleteContact(id, authentication.getName());
        return "redirect:/contacts";
    }

    @GetMapping("/create")
    public String showCreateContactForm(Model model) {
        model.addAttribute("contactCreateDTO", new ContactCreateDTO());
        return "create-contact";
    }

    @PostMapping("/create")
    public String createContact(
            @Valid @ModelAttribute("contactCreateDTO") ContactCreateDTO contactCreateDTO,
            BindingResult bindingResult,
            Authentication authentication,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "create-contact";
        }
        contactService.createContact(contactCreateDTO, authentication.getName());
        return "redirect:/contacts";
    }
}
