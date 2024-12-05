package ru.alex.NauJava.controllers.ui;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alex.NauJava.dto.address.AddressCreateDTO;
import ru.alex.NauJava.dto.address.AddressDTO;
import ru.alex.NauJava.dto.address.AddressUpdateDTO;
import ru.alex.NauJava.services.AddressService;

@Controller
@RequestMapping("/address")
public class AddressUIController {

    private final AddressService addressService;

    @Autowired
    public AddressUIController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/add/{contactId}")
    public String showAddAddressForm(@PathVariable Long contactId, Model model) {
        AddressCreateDTO addressCreateDTO = new AddressCreateDTO();
        addressCreateDTO.setContactId(contactId);
        model.addAttribute("address", addressCreateDTO);
        return "add-address";
    }

    @PostMapping("/add")
    public String addAddress(
            @Valid @ModelAttribute("address") AddressCreateDTO addressCreateDTO,
            BindingResult bindingResult,
            Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "add-address";
        }
        addressService.createAddress(addressCreateDTO, authentication.getName());
        return "redirect:/contacts/" + addressCreateDTO.getContactId();
    }

    @GetMapping("/edit/{id}")
    public String showEditAddressForm(@PathVariable Long id,
                                      @RequestParam Long contactId,
                                      Authentication authentication,
                                      Model model) {
        AddressDTO addressDTO = addressService.getAddressById(id, authentication.getName());
        model.addAttribute("address", addressDTO);
        model.addAttribute("contactId", contactId);
        return "edit-address";
    }

    @PostMapping("/edit")
    public String editAddress(
            @Valid @ModelAttribute("address") AddressUpdateDTO addressUpdateDTO,
            @RequestParam Long contactId,
            BindingResult bindingResult,
            Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "edit-address";
        }
        addressService.updateAddress(addressUpdateDTO, authentication.getName());
        return "redirect:/contacts/" + contactId;
    }

    @PostMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Long id, @RequestParam Long contactId, Authentication authentication) {
        addressService.deleteAddress(id, authentication.getName());
        return "redirect:/contacts/" + contactId;
    }
}
