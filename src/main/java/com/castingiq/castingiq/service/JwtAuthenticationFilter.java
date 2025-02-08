package com.castingiq.castingiq.service;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.castingiq.castingiq.model.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil; // Utility for working with JWT

    // Constructor to inject JwtUtil
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // The main method that processes each incoming request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Extract JWT from the request (Authorization header)
        String token = getTokenFromRequest(request);

        // If a token exists and is valid, set authentication context
        if (token != null && jwtUtil.validateToken(token, getUsernameFromToken(token))) {
            // Create an authentication token from the username extracted from the token
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    getUsernameFromToken(token), null, null);

            // Set the details for the authentication token
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set the authentication in the SecurityContext (which will be used in the application)
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }

    // Helper method to extract the token from the Authorization header
    private String getTokenFromRequest(HttpServletRequest request) {
        // Look for "Authorization" header and extract the token if it's in the format "Bearer <token>"
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Remove "Bearer " prefix and return the token
        }
        return null;
    }

    // Helper method to extract the username from the token
    private String getUsernameFromToken(String token) {
        return jwtUtil.extractUsername(token); // This uses JwtUtil to extract the username
    }
}
