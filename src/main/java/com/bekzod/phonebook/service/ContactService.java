package com.bekzod.phonebook.service;

import com.bekzod.phonebook.dto.ContactDTO;
import com.bekzod.phonebook.dto.SearchDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ContactService {

    Page<ContactDTO> getContacts(Integer page, Integer size);

    ContactDTO addContact(ContactDTO contactDTO);

    void deleteContact(UUID id);

    ContactDTO updateContact(UUID id, ContactDTO contactDTO);

    ContactDTO getContactById(UUID id);

    Page<ContactDTO> searchedContacts(SearchDTO searchDTO);
}
