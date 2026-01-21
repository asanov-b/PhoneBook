package com.bekzod.phonebook.repository;

import com.bekzod.phonebook.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}