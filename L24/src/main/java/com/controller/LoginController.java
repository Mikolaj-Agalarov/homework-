package com.controller;

import jakarta.servlet.http.HttpSession;
import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String getLoginUser(Model model, User user) {
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public String postLoginUser(User user, HttpSession session) {
        Optional<User> userByUsernamAndPassword = userService.getUserByUsernameAndPassword(user.getUsername().trim(), user.getPassword().trim());
        if(userByUsernamAndPassword.isPresent()) {
            System.out.println("user: " + userByUsernamAndPassword.get());
            session.setAttribute("user", userByUsernamAndPassword.get());
            return "redirect:/users/users";
        } else {
            return "redirect:/login/login";
        }
    }

}
