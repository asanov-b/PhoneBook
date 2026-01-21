package com.bekzod.phonebook.service;

import com.bekzod.phonebook.dto.LoginDTO;
import com.bekzod.phonebook.dto.LoginResDTO;
import com.bekzod.phonebook.dto.RegisterDTO;

public interface AuthService {
    LoginResDTO login(LoginDTO loginDTO);

    void register(RegisterDTO registerDTO);
}
