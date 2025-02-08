package com.castingiq.castingiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.castingiq.castingiq.model.UserLogin;

public interface UserRepository extends JpaRepository<UserLogin, Long> {
    UserLogin findByUsername(String username);  // Custom query to find user by username
}
