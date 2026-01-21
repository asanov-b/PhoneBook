package com.bekzod.phonebook.dto;

import jakarta.validation.constraints.NotBlank;
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
