package com.example.phonebook.service.interfaces;

import com.example.phonebook.dto.ContactDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ContactService {

    Page<ContactDTO> getContacts(Integer page, Integer size);

    ContactDTO addContact(ContactDTO contactDTO);

    void deleteContact(UUID id);

    ContactDTO updateContact(UUID id, ContactDTO contactDTO);

    ContactDTO getContactById(UUID id);

    Page<ContactDTO> searchedContacts(String firstName, String lastName, String phoneNumber, String country, String city, String street, Integer page, Integer size);
}
