package ru.alex.NauJava.controllers.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alex.NauJava.services.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {
        try {
            userService.addUser(username, password);
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("message", "Такой пользователь уже существует");
            return "registration";
        }
    }
}
