package com.bekzod.phonebook.dto;

import lombok.Builder;
import lombok.Data;

import static com.bekzod.phonebook.utils.AppConstants.*;

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