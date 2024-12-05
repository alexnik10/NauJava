package ru.alex.NauJava.controllers.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.alex.NauJava.dto.phone.PhoneCreateDTO;
import ru.alex.NauJava.dto.phone.PhoneDTO;
import ru.alex.NauJava.dto.phone.PhoneUpdateDTO;
import ru.alex.NauJava.services.PhoneService;

import java.util.List;

@RestController
@RequestMapping("/api/phones")
public class PhoneController {

    private final PhoneService phoneService;

    @Autowired
    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @PostMapping("/create")
    public ResponseEntity<PhoneDTO> createPhone(@Valid @RequestBody PhoneCreateDTO phoneCreateDTO, Authentication authentication) {
        PhoneDTO result = phoneService.createPhone(phoneCreateDTO, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/contact/{contactId}")
    public ResponseEntity<List<PhoneDTO>> GetPhones(Long contactId, Authentication authentication) {
        List<PhoneDTO> result = phoneService.getAllPhonesByContact(contactId, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<PhoneDTO> updatePhone(@Valid @RequestBody PhoneUpdateDTO phoneUpdateDTO, Authentication authentication) {
        PhoneDTO result = phoneService.updatePhone(phoneUpdateDTO, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneDTO> getPhoneById(@PathVariable Long id, Authentication authentication) {
        PhoneDTO result = phoneService.getPhoneById(id, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Long> deletePhone(@PathVariable Long id, Authentication authentication) {
        phoneService.deletePhone(id, authentication.getName());
        return ResponseEntity.ok(id);
    }
}
