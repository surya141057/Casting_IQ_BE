package com.castingiq.castingiq.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.castingiq.castingiq.model.UserLogin;
import com.castingiq.castingiq.repository.UserRepository;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    // Method to register a new user with password encryption
    public void registerUser(UserLogin user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        
        String rawPassword = user.getPassword(); // Ensure this is not null
        String encodedPassword = passwordEncoder.encode(rawPassword);  // Make sure passwordEncoder is properly initialized
    
        user.setPassword(encodedPassword);
    
        // Save the user and return
        userRepository.save(user);
    }
}

