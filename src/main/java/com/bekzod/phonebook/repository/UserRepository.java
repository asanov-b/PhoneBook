package com.bekzod.phonebook.repository;

import com.bekzod.phonebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
}