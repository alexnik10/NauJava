package ru.alex.NauJava.controllers.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.alex.NauJava.dto.contact.ContactDTO;
import ru.alex.NauJava.dto.group.GroupCreateDTO;
import ru.alex.NauJava.dto.group.GroupDTO;
import ru.alex.NauJava.dto.group.GroupUpdateDTO;
import ru.alex.NauJava.services.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/create")
    public ResponseEntity<GroupDTO> createGroup(@Valid @RequestBody GroupCreateDTO groupCreateDTO, Authentication authentication) {
        GroupDTO result = groupService.createGroup(groupCreateDTO, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getUserGroups(Authentication authentication) {
        List<GroupDTO> result = groupService.getAllGroupsByUsername(authentication.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<GroupDTO> updateGroup(@Valid @RequestBody GroupUpdateDTO groupUpdateDTO, Authentication authentication) {
        GroupDTO result = groupService.updateGroup(groupUpdateDTO, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id, Authentication authentication) {
        GroupDTO groupDTO = groupService.getGroupById(id, authentication.getName());
        return ResponseEntity.ok(groupDTO);
    }

    @GetMapping("/{id}/contacts")
    public ResponseEntity<List<ContactDTO>> getGroupContacts(@PathVariable Long id, Authentication authentication) {
        List<ContactDTO> result = groupService.getAllContactsByGroup(id, authentication.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/add-contact/{contactId}")
    public ResponseEntity<Void> addContactToGroup(@PathVariable Long id, @PathVariable Long contactId, Authentication authentication) {
        groupService.addContactToGroup(id, contactId, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/remove-contact/{contactId}")
    public ResponseEntity<Void> removeContactFromGroup(@PathVariable Long id, @PathVariable Long contactId, Authentication authentication) {
        groupService.removeContactFromGroup(id, contactId, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Long> deleteGroup(@PathVariable Long id, Authentication authentication) {
        groupService.deleteGroup(id, authentication.getName());
        return ResponseEntity.ok(id);
    }
}
