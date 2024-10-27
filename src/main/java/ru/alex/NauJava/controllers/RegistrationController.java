package ru.alex.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alex.NauJava.enums.Role;
import ru.alex.NauJava.services.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") Role role,
            Model model) {
        try {
            userService.addUser(username, password, role);
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("message", "User already exists");
            model.addAttribute("roles", Role.values());
            return "registration";
        }
    }
}
