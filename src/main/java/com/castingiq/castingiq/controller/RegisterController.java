package com.castingiq.castingiq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.castingiq.castingiq.model.UserLogin;
import com.castingiq.castingiq.response.UnauthorizedResponse;
import com.castingiq.castingiq.service.Exception.UniqueConstraintViolationException;
import com.castingiq.castingiq.service.UserService;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; 
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserLogin user) {
        try {
        userService.registerUser(user);  // Register the user with encrypted password
        return ResponseEntity.ok().body(new UnauthorizedResponse
("User "+user.getUsername()+ " registered successfully"));  // After registration, redirect to login
        }
        catch(UniqueConstraintViolationException e){
           return new ResponseEntity<>(new UnauthorizedResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
