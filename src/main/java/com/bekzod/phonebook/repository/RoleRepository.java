package com.bekzod.phonebook.repository;

import com.bekzod.phonebook.entity.Role;
import com.bekzod.phonebook.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    List<Role> findByRole(RoleName role);
}