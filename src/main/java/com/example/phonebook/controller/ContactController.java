package com.example.phonebook.controller;

import com.example.phonebook.dto.ContactDTO;
import com.example.phonebook.dto.SearchDTO;
import com.example.phonebook.service.interfaces.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.phonebook.utils.AppConstants.*;
import static org.springframework.http.ResponseEntity.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(API_PATH + API_VERSION + CONTACT)
public class ContactController {

    private final ContactService contactService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<Page<ContactDTO>> allContacts(@RequestParam Integer page, @RequestParam Integer size) {
        log.info("GET {} page={}, size={}", API_PATH + API_VERSION + CONTACT, page, size);
        Page<ContactDTO> contactDTOs = contactService.getContacts(page, size);
        return ok(contactDTOs);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable UUID id) {
        log.info("GET {}/{}", API_PATH + API_VERSION + CONTACT, id);
        ContactDTO contactDTO = contactService.getContactById(id);
        return ok(contactDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/search")
    public ResponseEntity<Page<ContactDTO>> search( SearchDTO searchDTO) {
        log.info("GET {}/search page={}, size={}", API_PATH + API_VERSION + CONTACT, searchDTO.getPage(), searchDTO.getSize());

        log.debug("Search params: firstName={}, lastName={}, phone={}, country={}, city={}, street={}",
                searchDTO.getFirstName(), searchDTO.getLastName(), searchDTO.getPhoneNumber(), searchDTO.getCountry(), searchDTO.getCity(), searchDTO.getStreet());

        Page<ContactDTO> contacts = contactService.searchedContacts(searchDTO);
        return ok(contacts);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@Valid @RequestBody ContactDTO contactDTO) {
        log.info("POST {} request received");
        ContactDTO newContact = contactService.addContact(contactDTO);
        return status(HttpStatus.CREATED).body(newContact);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable UUID id) {
        log.info("DELETE {}/{}", API_PATH + API_VERSION + CONTACT, id);
        contactService.deleteContact(id);
        return noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable UUID id, @Valid @RequestBody ContactDTO contactDTO) {
        log.info("PUT {}/{}", API_PATH + API_VERSION + CONTACT, id);
        ContactDTO update = contactService.updateContact(id, contactDTO);
        return ok(update);
    }
}
