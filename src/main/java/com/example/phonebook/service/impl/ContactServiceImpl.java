package com.example.phonebook.service.impl;

import com.example.phonebook.dto.ContactDTO;
import com.example.phonebook.entity.Address;
import com.example.phonebook.entity.Contact;
import com.example.phonebook.repository.ContactRepository;
import com.example.phonebook.repository.UserRepository;
import com.example.phonebook.service.interfaces.ContactService;
import com.example.phonebook.utils.ContactMapper;
import com.example.phonebook.utils.SecurityUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final UserRepository userRepository;

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
    public Page<ContactDTO> searchedContacts(String firstName, String lastName, String phoneNumber, String country, String city, String street, Integer page, Integer size) {

        Specification<Contact> spec = (root, query, cb) -> cb.conjunction();

        if (firstName != null && !firstName.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("firstName"), "%" + firstName + "%"));
        }
        if (lastName != null && !lastName.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("lastName"), "%" + lastName + "%"));
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("phoneNumber"), "%" + phoneNumber + "%"));
        }
        if (country != null && !country.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("address").get("country"), country));
        }
        if (city != null && !city.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("address").get("city"), city));
        }
        if (street != null && !street.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("address").get("street"), street));
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Contact> all = contactRepository.findAll(spec, pageable);
        return all.map(contactMapper::toDTO);
    }

    @Override
    public ContactDTO addContact(ContactDTO contactDTO) {

        if (contactRepository.existsByPhoneNumber(contactDTO.getPhoneNumber())) {
            throw new EntityExistsException("PhoneNumber already exists");
        }

        Contact entity = contactMapper.toEntity(contactDTO);
        Contact contact = contactRepository.save(entity);

        return contactMapper.toDTO(contact);
    }

    @Override
    public void deleteContact(UUID id) {
        contactRepository.deleteById(id);
    }

    @Override
    public ContactDTO updateContact(UUID id, ContactDTO contactDTO) {

        Optional<Contact> byId = contactRepository.findById(id);
        Contact contact = byId.orElseThrow(() -> new EntityNotFoundException("Contact not found"));

        if (contactRepository.existsByPhoneNumberAndIdIsNot(contactDTO.getPhoneNumber(), id)) {
            throw new EntityExistsException("PhoneNumber already exists");
        }

        contactMapper.forUpdate(contact, contactDTO);

        /*contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        contact.setUpdatedBy(userRepository.findByUsername(SecurityUtil.getCurrentUser()));

        Address address = contact.getAddress();
        address.setCountry(contactDTO.getCountry());
        address.setCity(contactDTO.getCity());
        address.setStreet(contactDTO.getStreet());
        address.setUpdatedBy(userRepository.findByUsername(SecurityUtil.getCurrentUser()));
        contact.setAddress(address);*/
        Contact saved = contactRepository.save(contact);

        return contactMapper.toDTO(saved);
    }


}
