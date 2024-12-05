package ru.alex.NauJava.services;

import ru.alex.NauJava.dto.contact.ContactDTO;
import ru.alex.NauJava.dto.group.GroupCreateDTO;
import ru.alex.NauJava.dto.group.GroupDTO;
import ru.alex.NauJava.dto.group.GroupUpdateDTO;

import java.util.List;

public interface GroupService {
    GroupDTO createGroup(GroupCreateDTO groupCreateDTO, String username);
    GroupDTO updateGroup(GroupUpdateDTO groupUpdateDTO, String username);
    void deleteGroup(Long groupId, String username);
    GroupDTO getGroupById(Long groupId, String username);
    List<GroupDTO> getAllGroupsByUsername(String username);
    List<ContactDTO> getAllContactsByGroup(Long groupId, String username);
    void addContactToGroup(Long groupId, Long contactId, String username);
    void removeContactFromGroup(Long groupId, Long contactId, String username);
}
