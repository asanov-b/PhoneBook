package com.bekzod.phonebook.service.impl;

import com.bekzod.phonebook.dto.ContactDTO;
import com.bekzod.phonebook.dto.SearchDTO;
import com.bekzod.phonebook.entity.Address;
import com.bekzod.phonebook.entity.Contact;
import com.bekzod.phonebook.repository.ContactRepository;
import com.bekzod.phonebook.mapper.ContactMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private ContactServiceImpl contactService;

    UUID id;
    ContactDTO contactDTO;
    Contact contact;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        contactDTO = new ContactDTO(
                id, "firstName", "lastName", "phoneNumber",
                "country", "city", "street"
        );

        contact = new Contact(
                "firstName",
                "lastName",
                "phoneNumber",
                Address.builder()
                        .country("country")
                        .city("city")
                        .street("street")
                        .build()
        );
    }

    @Test
    void addContact() {
        Contact savedContact = new Contact("firstName", "lastName", "phoneNumber", Address.builder().country("country").city("city").street("street").build());
        savedContact.setId(id);

        Mockito.when(contactMapper.toEntity(contactDTO))
                .thenReturn(contact);
        Mockito.when(contactRepository.save(contact))
                .thenReturn(savedContact);
        Mockito.when(contactMapper.toDTO(savedContact))
                .thenReturn(contactDTO);

        ContactDTO result = contactService.addContact(contactDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("firstName", result.getFirstName());
        Assertions.assertEquals("phoneNumber", result.getPhoneNumber());

        Mockito.verify(contactMapper).toEntity(contactDTO);
        Mockito.verify(contactRepository).save(contact);
        Mockito.verify(contactMapper).toDTO(savedContact);
    }

    @Test
    void addContact_whenPhoneNumberExists() {
        Mockito.when(contactRepository.existsByPhoneNumber("phoneNumber"))
                .thenReturn(true);

        Assertions.assertThrows(EntityExistsException.class, () -> contactService.addContact(contactDTO));

        Mockito.verify(contactRepository).existsByPhoneNumber("phoneNumber");
    }

    @Test
    void getContacts() {
        Page<Contact> contactPage = new PageImpl<>(List.of(new Contact(), new Contact()));

        Mockito.when(contactRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(contactPage);
        Mockito.when(contactMapper.toDTO(Mockito.any(Contact.class)))
                .thenReturn(contactDTO);

        Page<ContactDTO> contacts = contactService.getContacts(0, 10);

        Assertions.assertEquals(contactPage.getTotalElements(), contacts.getTotalElements());

        Mockito.verify(contactRepository).findAll(Mockito.any(Pageable.class));
        Mockito.verify(contactMapper, Mockito.times(contactPage.getContent().size())).toDTO(Mockito.any(Contact.class));
    }

    @Test
    void getContactById() {
        contact.setId(id);

        Mockito.when(contactRepository.findById(id))
                .thenReturn(Optional.of(contact));
        Mockito.when(contactMapper.toDTO(contact))
                .thenReturn(contactDTO);

        ContactDTO contactById = contactService.getContactById(id);

        Assertions.assertEquals(id, contactById.getId());

        Mockito.verify(contactMapper).toDTO(contact);
    }

    @Test
    void getContactById_whenIdNotFound() {
        Mockito.when(contactRepository.findById(id))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> contactService.getContactById(id));
    }

    @Test
    void searchedContacts() {

        SearchDTO searchDTO = SearchDTO.builder()
                .firstName("firstName")
                .lastName("lastName")
                .phoneNumber("phoneNumber")
                .country("country")
                .city("city")
                .street("street")
                .page(0)
                .size(10)
                .build();

        List<Contact> contactList = List.of(contact);
        Page<Contact> contactPage = new PageImpl<>(contactList);

        Mockito.when(contactRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(contactPage);

        Mockito.when(contactMapper.toDTO(contact))
                .thenReturn(contactDTO);

        Page<ContactDTO> result = contactService.searchedContacts(searchDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(contactPage.getTotalElements(), result.getTotalElements());
        Assertions.assertEquals("firstName", result.getContent().get(0).getFirstName());

        Mockito.verify(contactRepository).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
        Mockito.verify(contactMapper, Mockito.times(contactList.size())).toDTO(contact);
    }

    @Test
    void deleteContact() {
        contactService.deleteContact(id);
        Mockito.verify(contactRepository).deleteById(id);
    }

    @Test
    void updateContact() {
        Mockito.when(contactRepository.findById(id))
                .thenReturn(Optional.of(contact));
        Mockito.when(contactRepository.existsByPhoneNumberAndIdIsNot(contactDTO.getPhoneNumber(), id))
                .thenReturn(false);
        Mockito.doNothing().when(contactMapper).forUpdate(contact, contactDTO);
        Contact savedContact = contact;
        Mockito.when(contactRepository.save(contact))
                .thenReturn(savedContact);
        Mockito.when(contactMapper.toDTO(savedContact))
                .thenReturn(contactDTO);

        ContactDTO result = contactService.updateContact(id, contactDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(contactDTO.getId(), result.getId());
        Assertions.assertEquals(contactDTO.getPhoneNumber(), result.getPhoneNumber());

        Mockito.verify(contactRepository).findById(id);
        Mockito.verify(contactRepository).existsByPhoneNumberAndIdIsNot(contactDTO.getPhoneNumber(), id);
        Mockito.verify(contactMapper).forUpdate(contact, contactDTO);
        Mockito.verify(contactRepository).save(contact);
        Mockito.verify(contactMapper).toDTO(savedContact);
    }

    @Test
    void updateContact_whenNotFound() {
        Mockito.when(contactRepository.findById(id))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> contactService.updateContact(id, contactDTO));

        Mockito.verify(contactRepository).findById(id);
        Mockito.verifyNoMoreInteractions(contactRepository);
        Mockito.verifyNoInteractions(contactMapper);
    }

    @Test
    void updateContact_whenPhoneNumberExists() {
        Mockito.when(contactRepository.findById(id))
                .thenReturn(Optional.of(contact));
        Mockito.when(contactRepository.existsByPhoneNumberAndIdIsNot(contactDTO.getPhoneNumber(), id))
                .thenReturn(true);

        Assertions.assertThrows(EntityExistsException.class,
                () -> contactService.updateContact(id, contactDTO));

        Mockito.verify(contactRepository).findById(id);
        Mockito.verify(contactRepository).existsByPhoneNumberAndIdIsNot(contactDTO.getPhoneNumber(), id);
        Mockito.verifyNoMoreInteractions(contactRepository);
        Mockito.verifyNoInteractions(contactMapper);
    }
}