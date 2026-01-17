package com.example.phonebook.controller;

import com.example.phonebook.dto.ContactDTO;
import com.example.phonebook.service.interfaces.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.phonebook.utils.AppConstants.*;
import static org.springframework.http.ResponseEntity.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_PATH + API_VERSION + CONTACT)
public class ContactController {

    private final ContactService contactService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<Page<ContactDTO>> allContacts(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<ContactDTO> contactDTOs = contactService.getContacts(page, size);
        return ok(contactDTOs);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable UUID id) {
        ContactDTO contactDTO = contactService.getContactById(id);
        return ok(contactDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/search")
    public ResponseEntity<Page<ContactDTO>> search(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String street,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        System.out.printf("firstName: %s\nlastName: %s\nphoneNumber: %s\ncountry: %s\ncity: %s\nstreet: %s\n ", firstName, lastName, phoneNumber, country, city, street);
        Page<ContactDTO> contacts = contactService.searchedContacts(firstName, lastName, phoneNumber, country, city, street, page, size);
        return ok(contacts);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@Valid @RequestBody ContactDTO contactDTO) {
        ContactDTO newContact = contactService.addContact(contactDTO);
        return status(HttpStatus.CREATED).body(newContact);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable UUID id) {
        contactService.deleteContact(id);
        return noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable UUID id, @Valid @RequestBody ContactDTO contactDTO) {
        ContactDTO update = contactService.updateContact(id, contactDTO);
        return ok(update);
    }
}
