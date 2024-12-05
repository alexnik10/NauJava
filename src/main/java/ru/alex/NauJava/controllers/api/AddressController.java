package ru.alex.NauJava.controllers.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.alex.NauJava.dto.address.AddressCreateDTO;
import ru.alex.NauJava.dto.address.AddressDTO;
import ru.alex.NauJava.dto.address.AddressUpdateDTO;
import ru.alex.NauJava.services.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/create")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressCreateDTO addressCreateDTO, Authentication authentication) {
        AddressDTO result = addressService.createAddress(addressCreateDTO, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/contact/{contactId}")
    public ResponseEntity<List<AddressDTO>> GetAddresses(Long contactId, Authentication authentication) {
        List<AddressDTO> result = addressService.getAllAddressesByContact(contactId, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<AddressDTO> updateAddress(@Valid @RequestBody AddressUpdateDTO addressUpdateDTO, Authentication authentication) {
        AddressDTO result = addressService.updateAddress(addressUpdateDTO, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id, Authentication authentication) {
        AddressDTO result = addressService.getAddressById(id, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Long> deleteAddress(@PathVariable Long id, Authentication authentication) {
        addressService.deleteAddress(id, authentication.getName());
        return ResponseEntity.ok(id);
    }
}
