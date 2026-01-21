package com.bekzod.phonebook.mapper;

import com.bekzod.phonebook.dto.ContactDTO;
import com.bekzod.phonebook.entity.Address;
import com.bekzod.phonebook.entity.Contact;
import com.bekzod.phonebook.repository.UserRepository;
import com.bekzod.phonebook.utils.SecurityUtil;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    private final UserRepository userRepository;

    public ContactMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ContactDTO toDTO(Contact contact) {
        return new ContactDTO(
                contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getPhoneNumber(),
                contact.getAddress().getCountry(),
                contact.getAddress().getCity(),
                contact.getAddress().getStreet()
        );
    }

    public Contact toEntity(ContactDTO contactDTO) {
        Address address = Address.builder()
                .country(contactDTO.getCountry())
                .city(contactDTO.getCity())
                .street(contactDTO.getStreet())
                .build();
        address.setCreatedBy(userRepository.findByUsername(SecurityUtil.getCurrentUser()));
        Contact contact = Contact.builder()
                .firstName(contactDTO.getFirstName())
                .lastName(contactDTO.getLastName())
                .phoneNumber(contactDTO.getPhoneNumber())
                .address(address)
                .build();
        contact.setCreatedBy(userRepository.findByUsername(SecurityUtil.getCurrentUser()));

        return contact;
    }

    public void forUpdate(Contact contact, ContactDTO contactDTO) {
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        contact.setUpdatedBy(userRepository.findByUsername(SecurityUtil.getCurrentUser()));

        Address address = contact.getAddress();
        address.setCountry(contactDTO.getCountry());
        address.setCity(contactDTO.getCity());
        address.setStreet(contactDTO.getStreet());
        address.setUpdatedBy(userRepository.findByUsername(SecurityUtil.getCurrentUser()));
        contact.setAddress(address);
    }
}
