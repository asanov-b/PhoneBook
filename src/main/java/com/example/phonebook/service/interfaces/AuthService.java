package com.example.phonebook.service.interfaces;

import com.example.phonebook.dto.LoginDTO;
import com.example.phonebook.dto.LoginResDTO;
import com.example.phonebook.dto.RegisterDTO;
import org.apache.coyote.BadRequestException;

public interface AuthService {
    LoginResDTO login(LoginDTO loginDTO);

    void register(RegisterDTO registerDTO);
}
