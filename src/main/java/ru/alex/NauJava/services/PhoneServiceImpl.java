package ru.alex.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.NauJava.dto.phone.PhoneCreateDTO;
import ru.alex.NauJava.dto.phone.PhoneDTO;
import ru.alex.NauJava.dto.phone.PhoneUpdateDTO;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.entities.Phone;
import ru.alex.NauJava.exceptions.ContactNotFoundException;
import ru.alex.NauJava.exceptions.PhoneNotFoundException;
import ru.alex.NauJava.repositories.ContactRepository;
import ru.alex.NauJava.repositories.PhoneRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public PhoneServiceImpl(PhoneRepository phoneRepository, ContactRepository contactRepository) {
        this.phoneRepository = phoneRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public PhoneDTO createPhone(PhoneCreateDTO phoneCreateDTO, String username) {
        Contact contact = contactRepository.findById(phoneCreateDTO.getContactId())
                .filter(c -> c.getUser().getUsername().equals(username))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + phoneCreateDTO.getContactId()));

        Phone phone = new Phone();
        phone.setContact(contact);
        phone.setNumber(phoneCreateDTO.getNumber());
        phone.setType(phoneCreateDTO.getType());
        Phone savedPhone = phoneRepository.save(phone);
        return mapToDTO(savedPhone);
    }

    @Override
    public List<PhoneDTO> getAllPhonesByContact(Long contactId, String username) {
        Contact contact = contactRepository.findById(contactId)
                .filter(c -> c.getUser().getUsername().equals(username))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + contactId));

        return phoneRepository.findAllByContactId(contactId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PhoneDTO getPhoneById(Long phoneId, String username) {
        Phone phone = phoneRepository.findById(phoneId)
                .filter(p -> p.getContact().getUser().getUsername().equals(username))
                .orElseThrow(() -> new PhoneNotFoundException("Phone not found with ID: " + phoneId));
        return mapToDTO(phone);
    }

    @Override
    public PhoneDTO updatePhone(PhoneUpdateDTO phoneUpdateDTO, String username) {
        Phone phone = phoneRepository.findById(phoneUpdateDTO.getId())
                .filter(p -> p.getContact().getUser().getUsername().equals(username))
                .orElseThrow(() -> new PhoneNotFoundException("Phone not found with ID: " + phoneUpdateDTO.getId()));

        phone.setNumber(phoneUpdateDTO.getNumber());
        phone.setType(phoneUpdateDTO.getType());
        Phone updatedPhone = phoneRepository.save(phone);
        return mapToDTO(updatedPhone);
    }

    @Override
    public void deletePhone(Long phoneId, String username) {
        Phone phone = phoneRepository.findById(phoneId)
                .filter(p -> p.getContact().getUser().getUsername().equals(username))
                .orElseThrow(() -> new PhoneNotFoundException("Phone not found with ID: " + phoneId));
        phoneRepository.deleteById(phoneId);
    }

    private PhoneDTO mapToDTO(Phone phone) {
        PhoneDTO dto = new PhoneDTO();
        dto.setId(phone.getId());
        dto.setNumber(phone.getNumber());
        dto.setType(phone.getType());
        return dto;
    }
}
