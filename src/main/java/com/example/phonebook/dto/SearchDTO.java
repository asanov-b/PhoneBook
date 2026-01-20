package com.example.phonebook.dto;

import lombok.Builder;
import lombok.Data;

import static com.example.phonebook.utils.AppConstants.*;

@Data
@Builder
public class SearchDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String country;
    private String city;
    private String street;
    private Integer page = SEARCH_PAGE;
    private Integer size = SEARCH_SIZE;
}