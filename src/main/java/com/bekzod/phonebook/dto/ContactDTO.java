package com.bekzod.phonebook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.util.UUID;

@Value
public class ContactDTO {

    UUID id;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @Pattern(regexp = "\\+998 \\d{2} \\d{3} \\d{2} \\d{2}")
    String phoneNumber;
    @NotBlank
    String country;
    @NotBlank
    String city;
    @NotBlank
    String street;
}
