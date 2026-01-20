package com.example.phonebook.service.impl;

import com.example.phonebook.dto.ContactDTO;
import com.example.phonebook.dto.SearchDTO;
import com.example.phonebook.entity.Contact;
import com.example.phonebook.repository.ContactRepository;
import com.example.phonebook.service.interfaces.ContactService;
import com.example.phonebook.utils.ContactMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    public Page<ContactDTO> getContacts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Contact> res = contactRepository.findAll(pageable);
        return res.map(contactMapper::toDTO);
    }

    @Override
    public ContactDTO getContactById(UUID id) {
        Optional<Contact> byId = contactRepository.findById(id);
        Contact contact = byId.orElseThrow(() -> new EntityNotFoundException("Contact not found"));
        return contactMapper.toDTO(contact);
    }

    @Override
    public Page<ContactDTO> searchedContacts(SearchDTO searchDTO) {

        Specification<Contact> spec = (root, query, cb) -> cb.conjunction();

        if (searchDTO.getFirstName() != null && !searchDTO.getFirstName().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("firstName"), "%" + searchDTO.getFirstName() + "%"));
        }
        if (searchDTO.getLastName() != null && !searchDTO.getLastName().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("lastName"), "%" + searchDTO.getLastName() + "%"));
        }
        if (searchDTO.getPhoneNumber() != null && !searchDTO.getPhoneNumber().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("phoneNumber"), "%" + searchDTO.getPhoneNumber() + "%"));
        }
        if (searchDTO.getCountry() != null && !searchDTO.getCountry().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("address").get("country"), searchDTO.getCountry()));
        }
        if (searchDTO.getCity() != null && !searchDTO.getCity().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("address").get("city"), searchDTO.getCity()));
        }
        if (searchDTO.getStreet() != null && !searchDTO.getStreet().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("address").get("street"), searchDTO.getStreet()));
        }

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize());

        Page<Contact> all = contactRepository.findAll(spec, pageable);
        return all.map(contactMapper::toDTO);
    }

    @Override
    public ContactDTO addContact(ContactDTO contactDTO) {

        log.info("Adding contact with phone={}", contactDTO.getPhoneNumber());
        if (contactRepository.existsByPhoneNumber(contactDTO.getPhoneNumber())) {
            log.warn("Phone number {} already exists", contactDTO.getPhoneNumber());
            throw new EntityExistsException("PhoneNumber already exists");
        }

        Contact entity = contactMapper.toEntity(contactDTO);
        Contact contact = contactRepository.save(entity);
        log.info("Contact id={} added successfully", contact.getId());

        return contactMapper.toDTO(contact);
    }

    @Override
    public void deleteContact(UUID id) {
        contactRepository.deleteById(id);
        log.info("Contact id={} deleted successfully", id);
    }

    @Override
    public ContactDTO updateContact(UUID id, ContactDTO contactDTO) {

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found"));

        if (contactRepository.existsByPhoneNumberAndIdIsNot(contactDTO.getPhoneNumber(), id)) {
            log.warn("Phone number {} already exists", contactDTO.getPhoneNumber());
            throw new EntityExistsException("PhoneNumber already exists");
        }

        contactMapper.forUpdate(contact, contactDTO);

        Contact saved = contactRepository.save(contact);
        log.info("Contact id={} updated successfully", saved.getId());

        return contactMapper.toDTO(saved);
    }

}
