package ru.alex.NauJava.controllers.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeUiController {
    @GetMapping("/")
    public String redirectToContactList() {
        return "redirect:/contacts";
    }
}
