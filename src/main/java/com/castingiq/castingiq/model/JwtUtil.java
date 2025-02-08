package com.castingiq.castingiq.model;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;  // Time in milliseconds for token expiration

    // Generate the token for a given username
    public String generateToken(String username) {
         byte[] keyBytes = secretKey.getBytes();
        
        if (keyBytes.length < 32) {  // If secret key is shorter than 256 bits
            keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded(); // Generate a secure key
        }
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(SignatureAlgorithm.HS256, keyBytes    )
            .compact();
    }

    // Validate the token and check if it's expired
    public boolean validateToken(String token, String username) {
        String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    // Extract the username from the token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Extract claims from the token
    private Claims extractClaims(String token) {
        return Jwts.parser()  // Using the older API
        .setSigningKey(secretKey)  // Set the signing key
        .parseClaimsJws(token)
        .getBody();
    }
}

