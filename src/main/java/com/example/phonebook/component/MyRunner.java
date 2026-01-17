package com.example.phonebook.component;

import com.example.phonebook.dto.RegisterDTO;
import com.example.phonebook.entity.Address;
import com.example.phonebook.entity.Contact;
import com.example.phonebook.entity.Role;
import com.example.phonebook.entity.enums.RoleName;
import com.example.phonebook.repository.ContactRepository;
import com.example.phonebook.repository.RoleRepository;
import com.example.phonebook.repository.UserRepository;
import com.example.phonebook.service.interfaces.AuthService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyRunner implements CommandLineRunner {

    private final ContactRepository contactRepository;
    private final AuthService authService;
    private final RoleRepository roleRepository;
    Faker faker = new Faker();

    @Override
    public void run(String... args) throws Exception {

        /*List<Contact> contacts = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            contacts.add(
                    Contact.builder()
                            .firstName(faker.name().firstName())
                            .lastName(faker.name().lastName())
                            .phoneNumber(faker.regexify("\\+998 (90|91|93|94|95|99) \\d{3} \\d{2} \\d{2}"))
                            .address(
                                    Address.builder()
                                            .country(faker.address().country())
                                            .city(faker.address().city())
                                            .street(faker.address().streetName())
                                            .build()
                            )
                            .build()
            );
        }
        contactRepository.saveAll(contacts);*/

        /*RegisterDTO user = new RegisterDTO("user", "user", "user", faker.name().firstName(), faker.name().lastName());
        authService.register(user);
        RegisterDTO admin = new RegisterDTO("admin", "admin", "admin", faker.name().firstName(), faker.name().lastName());
        authService.register(admin);*/
    }
}
