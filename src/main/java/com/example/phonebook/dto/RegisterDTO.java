package com.example.phonebook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class RegisterDTO {

    @NotBlank
    String username;
    @NotBlank
    String password;
    @NotBlank
    String confirmPassword;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
}
