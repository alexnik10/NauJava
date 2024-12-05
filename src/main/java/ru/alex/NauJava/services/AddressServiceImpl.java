package ru.alex.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.NauJava.dto.address.AddressCreateDTO;
import ru.alex.NauJava.dto.address.AddressDTO;
import ru.alex.NauJava.dto.address.AddressUpdateDTO;
import ru.alex.NauJava.entities.Address;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.exceptions.AddressNotFoundException;
import ru.alex.NauJava.exceptions.ContactNotFoundException;
import ru.alex.NauJava.repositories.AddressRepository;
import ru.alex.NauJava.repositories.ContactRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, ContactRepository contactRepository) {
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public AddressDTO createAddress(AddressCreateDTO addressCreateDTO, String username) {
        Contact contact = contactRepository.findById(addressCreateDTO.getContactId())
                .filter(c -> c.getUser().getUsername().equals(username))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + addressCreateDTO.getContactId()));

        Address address = new Address();
        address.setContact(contact);
        address.setStreet(addressCreateDTO.getStreet());
        address.setCity(addressCreateDTO.getCity());
        address.setPostalCode(addressCreateDTO.getPostalCode());
        address.setCountry(addressCreateDTO.getCountry());
        Address savedAddress = addressRepository.save(address);
        return mapToDTO(savedAddress);
    }

    @Override
    public List<AddressDTO> getAllAddressesByContact(Long contactId, String username) {
        Contact contact = contactRepository.findById(contactId)
                .filter(c -> c.getUser().getUsername().equals(username))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + contactId));

        return addressRepository.findAllByContactId(contactId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO getAddressById(Long addressId, String username) {
        Address address = addressRepository.findById(addressId)
                .filter(a -> a.getContact().getUser().getUsername().equals(username))
                .orElseThrow(() -> new AddressNotFoundException("Address not found with ID: " + addressId));
        return mapToDTO(address);
    }

    @Override
    public AddressDTO updateAddress(AddressUpdateDTO addressUpdateDTO, String username) {
        Address address = addressRepository.findById(addressUpdateDTO.getId())
                .filter(a -> a.getContact().getUser().getUsername().equals(username))
                .orElseThrow(() -> new AddressNotFoundException("Address not found with ID: " + addressUpdateDTO.getId()));

        address.setStreet(addressUpdateDTO.getStreet());
        address.setCity(addressUpdateDTO.getCity());
        address.setPostalCode(addressUpdateDTO.getPostalCode());
        address.setCountry(addressUpdateDTO.getCountry());
        Address updatedAddress = addressRepository.save(address);
        return mapToDTO(updatedAddress);
    }

    @Override
    public void deleteAddress(Long addressId, String username) {
        Address address = addressRepository.findById(addressId)
                .filter(a -> a.getContact().getUser().getUsername().equals(username))
                .orElseThrow(() -> new AddressNotFoundException("Address not found with ID: " + addressId));
        addressRepository.deleteById(addressId);
    }

    private AddressDTO mapToDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        return dto;
    }
}
