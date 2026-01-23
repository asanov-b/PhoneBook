package com.bekzod.phonebook.component;

import com.bekzod.phonebook.entity.Address;
import com.bekzod.phonebook.entity.Contact;
import com.bekzod.phonebook.entity.Role;
import com.bekzod.phonebook.entity.User;
import com.bekzod.phonebook.entity.enums.RoleName;
import com.bekzod.phonebook.repository.ContactRepository;
import com.bekzod.phonebook.repository.RoleRepository;
import com.bekzod.phonebook.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyRunner implements CommandLineRunner {

    private final ContactRepository contactRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    Faker faker = new Faker();

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.save(new Role(RoleName.ADMIN));
            Role userRole = roleRepository.save(new Role(RoleName.USER));

            User user = userRepository.save(new User("Bekzod", "Asanov", "user", passwordEncoder.encode("user"), List.of(userRole)));
            User admin = userRepository.save(new User("Bekzod", "Asanov", "admin", passwordEncoder.encode("admin"), List.of(adminRole, userRole)));

            List<Contact> contacts = new LinkedList<>();
            for (int i = 0; i < 100; i++) {
                Address address = Address.builder()
                        .country(faker.address().country())
                        .city(faker.address().city())
                        .street(faker.address().streetName())
                        .build();
                address.setCreatedBy(admin);
                Contact contact = Contact.builder()
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .phoneNumber(faker.regexify("\\+998 (90|91|93|94|95|99) \\d{3} \\d{2} \\d{2}"))
                        .address(address)
                        .build();
                contact.setCreatedBy(admin);
                contacts.add(contact);
            }
            contactRepository.saveAll(contacts);
        }
    }
}
