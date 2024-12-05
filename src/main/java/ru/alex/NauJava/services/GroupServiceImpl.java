package ru.alex.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.NauJava.dto.contact.ContactDTO;
import ru.alex.NauJava.dto.group.GroupCreateDTO;
import ru.alex.NauJava.dto.group.GroupDTO;
import ru.alex.NauJava.dto.group.GroupUpdateDTO;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.entities.Group;
import ru.alex.NauJava.entities.User;
import ru.alex.NauJava.exceptions.ContactNotFoundException;
import ru.alex.NauJava.exceptions.GroupNotFoundException;
import ru.alex.NauJava.repositories.ContactRepository;
import ru.alex.NauJava.repositories.GroupRepository;
import ru.alex.NauJava.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository, ContactRepository contactRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public GroupDTO createGroup(GroupCreateDTO groupCreateDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        Group group = new Group();
        group.setName(groupCreateDTO.getName());
        group.setDescription(groupCreateDTO.getDescription());
        group.setUser(user);
        Group savedGroup = groupRepository.save(group);
        return mapToDTO(savedGroup);
    }

    @Override
    public GroupDTO updateGroup(GroupUpdateDTO groupUpdateDTO, String username) {
        Group group = groupRepository.findById(groupUpdateDTO.getId())
                .filter(g -> g.getUser().getUsername().equals(username))
                .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupUpdateDTO.getId()));

        group.setName(groupUpdateDTO.getName());
        group.setDescription(groupUpdateDTO.getDescription());
        Group updatedGroup = groupRepository.save(group);
        return mapToDTO(updatedGroup);
    }

    @Override
    public void deleteGroup(Long groupId, String username) {
        Group group = groupRepository.findById(groupId)
                .filter(g -> g.getUser().getUsername().equals(username))
                .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupId));

        for (Contact contact : group.getContacts()) {
            contact.getGroups().remove(group);
        }
        group.getContacts().clear();
        groupRepository.save(group);

        groupRepository.delete(group);
    }

    @Override
    public GroupDTO getGroupById(Long groupId, String username) {
        Group group = groupRepository.findById(groupId)
                .filter(g -> g.getUser().getUsername().equals(username))
                .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupId));
        return mapToDTO(group);
    }

    @Override
    public List<GroupDTO> getAllGroupsByUsername(String username) {
        List<Group> groups = groupRepository.findAllByUserUsername(username);
        return groups.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactDTO> getAllContactsByGroup(Long groupId, String username) {
        Group group = groupRepository.findById(groupId)
                .filter(g -> g.getUser().getUsername().equals(username))
                .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupId));

        return group.getContacts()
                .stream()
                .map(this::mapContactToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void addContactToGroup(Long groupId, Long contactId, String username) {
        Group group = groupRepository.findById(groupId)
                .filter(g -> g.getUser().getUsername().equals(username))
                .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupId));
        Contact contact = contactRepository.findById(contactId)
                .filter(c -> c.getUser().getUsername().equals(username))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + contactId));

        if (!group.getContacts().contains(contact)) {
            group.getContacts().add(contact);
            contact.getGroups().add(group);
            groupRepository.save(group);
        }
    }

    @Override
    public void removeContactFromGroup(Long groupId, Long contactId, String username) {
        Group group = groupRepository.findById(groupId)
                .filter(g -> g.getUser().getUsername().equals(username))
                .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupId));
        Contact contact = contactRepository.findById(contactId)
                .filter(c -> c.getUser().getUsername().equals(username))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + contactId));

        if (group.getContacts().contains(contact)) {
            group.getContacts().remove(contact);
            contact.getGroups().remove(group);

            groupRepository.save(group);
        } else {
            throw new IllegalStateException("Contact is not part of the group");
        }
    }

    private GroupDTO mapToDTO(Group group) {
        GroupDTO dto = new GroupDTO();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        return dto;
    }

    private ContactDTO mapContactToDTO(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setMiddleName(contact.getMiddleName());
        dto.setBirthday(contact.getBirthday());
        return dto;
    }
}
