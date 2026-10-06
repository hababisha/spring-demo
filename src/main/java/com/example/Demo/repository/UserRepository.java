package com.example.Demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Demo.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
