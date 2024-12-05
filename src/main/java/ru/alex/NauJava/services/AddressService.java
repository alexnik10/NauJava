package ru.alex.NauJava.services;

import ru.alex.NauJava.dto.address.AddressCreateDTO;
import ru.alex.NauJava.dto.address.AddressDTO;
import ru.alex.NauJava.dto.address.AddressUpdateDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressCreateDTO addressCreateDTO, String username);

    List<AddressDTO> getAllAddressesByContact(Long contactId, String username);

    AddressDTO getAddressById(Long addressId, String username);

    AddressDTO updateAddress(AddressUpdateDTO addressUpdateDTO, String username);

    void deleteAddress(Long addressId, String username);
}
