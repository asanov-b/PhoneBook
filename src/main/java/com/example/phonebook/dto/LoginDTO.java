package com.example.phonebook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class LoginDTO {

    @NotBlank
    String username;

    @NotBlank
    String password;
}
