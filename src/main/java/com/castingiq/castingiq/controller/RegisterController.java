package com.castingiq.castingiq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.castingiq.castingiq.model.UserLogin;
import com.castingiq.castingiq.service.UserService;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";  // Render the register.html page
    }

    @PostMapping("/register")
    public String registerUser(UserLogin user) {
        System.out.println("Username: " + user.getUsername());
    System.out.println("Password: " + user.getPassword());
        userService.registerUser(user);  // Register the user with encrypted password
        return "redirect:/login";  // After registration, redirect to login
    }
}
