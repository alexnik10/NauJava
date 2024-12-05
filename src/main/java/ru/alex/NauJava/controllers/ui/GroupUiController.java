package ru.alex.NauJava.controllers.ui;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alex.NauJava.dto.contact.ContactDTO;
import ru.alex.NauJava.dto.group.GroupCreateDTO;
import ru.alex.NauJava.dto.group.GroupDTO;
import ru.alex.NauJava.dto.group.GroupUpdateDTO;
import ru.alex.NauJava.services.ContactService;
import ru.alex.NauJava.services.GroupService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/groups")
public class GroupUiController {

    private final GroupService groupService;
    private final ContactService contactService;

    @Autowired
    public GroupUiController(GroupService groupService, ContactService contactService) {
        this.groupService = groupService;
        this.contactService = contactService;
    }

    @GetMapping("/create")
    public String showCreateGroupForm(Model model) {
        model.addAttribute("group", new GroupCreateDTO());
        return "create-group";
    }

    @PostMapping("/create")
    public String createGroup(@ModelAttribute("group") @Valid GroupCreateDTO groupCreateDTO,
                              Authentication authentication) {
        groupService.createGroup(groupCreateDTO, authentication.getName());
        return "redirect:/groups";
    }

    @GetMapping
    public String listGroupsWithContacts(Authentication authentication, Model model) {
        List<GroupDTO> groups = groupService.getAllGroupsByUsername(authentication.getName());
        Map<Long, List<ContactDTO>> groupContacts = new HashMap<>();

        for (GroupDTO group : groups) {
            List<ContactDTO> contacts = groupService.getAllContactsByGroup(group.getId(), authentication.getName());
            groupContacts.put(group.getId(), contacts);
        }

        model.addAttribute("groups", groups);
        model.addAttribute("groupContacts", groupContacts);
        return "list-groups";
    }

    @GetMapping("/add-contact/{id}")
    public String showAddContactToGroupForm(@PathVariable Long id, Model model, Authentication authentication) {
        List<ContactDTO> availableContacts = contactService.getAllContactsByUsername(authentication.getName());
        model.addAttribute("groupId", id);
        model.addAttribute("contacts", availableContacts);
        return "add-contact-to-group";
    }

    @PostMapping("/add-contact/{id}")
    public String addContactToGroup(@PathVariable Long id,
                                    @RequestParam Long contactId,
                                    Authentication authentication) {
        groupService.addContactToGroup(id, contactId, authentication.getName());
        return "redirect:/groups";
    }

    @PostMapping("/remove-contact/{groupId}/{contactId}")
    public String removeContactFromGroup(@PathVariable Long groupId,
                                         @PathVariable Long contactId,
                                         Authentication authentication) {
        groupService.removeContactFromGroup(groupId, contactId, authentication.getName());
        return "redirect:/groups";
    }

    @GetMapping("/edit/{id}")
    public String showEditGroupForm(@PathVariable Long id, Model model, Authentication authentication) {
        GroupDTO group = groupService.getGroupById(id, authentication.getName());
        model.addAttribute("group", group);
        return "edit-group";
    }

    @PostMapping("/edit/{id}")
    public String editGroup(@PathVariable Long id,
                            @ModelAttribute("group") @Valid GroupUpdateDTO groupUpdateDTO,
                            BindingResult result,
                            Authentication authentication) {
        if (result.hasErrors()) {
            return "edit-group";
        }
        groupUpdateDTO.setId(id);
        groupService.updateGroup(groupUpdateDTO, authentication.getName());
        return "redirect:/groups";
    }

    @PostMapping("/delete/{id}")
    public String deleteGroup(@PathVariable Long id, Authentication authentication) {
        groupService.deleteGroup(id, authentication.getName());
        return "redirect:/groups";
    }
}
