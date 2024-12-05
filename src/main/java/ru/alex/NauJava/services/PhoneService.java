package ru.alex.NauJava.services;

import ru.alex.NauJava.dto.phone.PhoneCreateDTO;
import ru.alex.NauJava.dto.phone.PhoneDTO;
import ru.alex.NauJava.dto.phone.PhoneUpdateDTO;

import java.util.List;

public interface PhoneService {
    PhoneDTO createPhone(PhoneCreateDTO phoneCreateDTO, String username);

    List<PhoneDTO> getAllPhonesByContact(Long contactId, String username);

    PhoneDTO getPhoneById(Long phoneId, String username);

    PhoneDTO updatePhone(PhoneUpdateDTO phoneUpdateDTO, String username);

    void deletePhone(Long phoneId, String username);
}
