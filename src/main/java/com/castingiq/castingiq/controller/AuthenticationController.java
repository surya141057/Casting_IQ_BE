package com.castingiq.castingiq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.castingiq.castingiq.model.JwtUtil;
import com.castingiq.castingiq.model.UserLogin;
import com.castingiq.castingiq.repository.UserRepository;
import com.castingiq.castingiq.response.JwtResponse;
import com.castingiq.castingiq.response.UnauthorizedResponse;
import com.castingiq.castingiq.service.UserService;

@RestController
public class AuthenticationController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository; 

    @Autowired
    public AuthenticationController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin user) {
        try {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        ); 
        UserLogin userLogin = userRepository.findByUsername(user.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        // If authentication is successful, generate a JWT token
        String token = jwtUtil.generateToken(authentication.getName());

        // Return token as a response
        return ResponseEntity.ok(new JwtResponse("Success",userLogin.getId(),userLogin.getUsername(), token));
       } catch (BadCredentialsException e) {
        // Handle the case where authentication fails due to bad credentials
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new UnauthorizedResponse("Authentication failed: Invalid username or password"));

    } catch (LockedException e) {
        // Handle case where the account is locked
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new UnauthorizedResponse("Authentication failed: Account is locked"));

    } catch (org.springframework.security.authentication.AccountExpiredException e) {
        // Handle case where the account has expired
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new UnauthorizedResponse("Authentication failed: Account has expired"));

    } catch (CredentialsExpiredException e) {
        // Handle case where the credentials have expired
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new UnauthorizedResponse("Authentication failed: Credentials have expired"));

    } catch (org.springframework.security.core.AuthenticationException e) {
        // Catch any other general AuthenticationException
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new UnauthorizedResponse("Authentication failed: " + e.getMessage()));
    }
    }
}

