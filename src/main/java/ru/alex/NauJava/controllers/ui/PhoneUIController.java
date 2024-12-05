package ru.alex.NauJava.controllers.ui;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alex.NauJava.dto.phone.PhoneCreateDTO;
import ru.alex.NauJava.dto.phone.PhoneDTO;
import ru.alex.NauJava.dto.phone.PhoneUpdateDTO;
import ru.alex.NauJava.services.PhoneService;

@Controller
@RequestMapping("/phone")
public class PhoneUIController {

    private final PhoneService phoneService;

    @Autowired
    public PhoneUIController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping("/add/{contactId}")
    public String showAddPhoneForm(@PathVariable Long contactId, Model model) {
        PhoneCreateDTO phoneCreateDTO = new PhoneCreateDTO();
        phoneCreateDTO.setContactId(contactId);
        model.addAttribute("phone", phoneCreateDTO);
        return "add-phone";
    }

    @PostMapping("/add")
    public String addPhone(@Valid @ModelAttribute("phone") PhoneCreateDTO phoneCreateDTO,
                           BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "add-phone";
        }
        phoneService.createPhone(phoneCreateDTO, authentication.getName());
        return "redirect:/contacts/" + phoneCreateDTO.getContactId();
    }

    @GetMapping("/edit/{id}")
    public String showEditPhoneForm(@PathVariable Long id,
                                    @RequestParam Long contactId,
                                    Authentication authentication,
                                    Model model) {
        PhoneDTO phone = phoneService.getPhoneById(id, authentication.getName());
        model.addAttribute("phone", phone);
        model.addAttribute("contactId", contactId);
        return "edit-phone";
    }

    @PostMapping("/edit")
    public String editPhone(
            @Valid @ModelAttribute("phone") PhoneUpdateDTO phoneUpdateDTO,
            @RequestParam Long contactId,
            BindingResult bindingResult,
            Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "edit-phone";
        }
        phoneService.updatePhone(phoneUpdateDTO, authentication.getName());
        return "redirect:/contacts/" + contactId;
    }

    @PostMapping("/delete/{id}")
    public String deletePhone(@PathVariable Long id, @RequestParam Long contactId, Authentication authentication) {
        phoneService.deletePhone(id, authentication.getName());
        return "redirect:/contacts/" + contactId;
    }
}
