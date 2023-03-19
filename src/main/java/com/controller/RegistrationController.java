package com.controller;

import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerNewUser(Model model) {
        User user = new User();
        model.addAttribute("newUser", user);
        return "registration";
    }

    @PostMapping("register")
    public String registerUser(Model model, User user) {
        if (!userService.addUser(user)) {
            model.addAttribute("error", "Invalid username or password");
            return "redirect:/registration/register";
        } else {
            return "redirect:/login/login";
        }
    }


}
