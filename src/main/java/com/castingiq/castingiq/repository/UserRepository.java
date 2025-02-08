package com.castingiq.castingiq.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.castingiq.castingiq.model.UserLogin;

@Repository
public interface UserRepository extends JpaRepository<UserLogin, Long> {
    Optional<UserLogin> findByUsername(String username);  // Custom query to find user by username
}
