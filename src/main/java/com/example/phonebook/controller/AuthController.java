package com.example.phonebook.controller;

import com.example.phonebook.dto.LoginDTO;
import com.example.phonebook.dto.LoginResDTO;
import com.example.phonebook.dto.RegisterDTO;
import com.example.phonebook.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.phonebook.utils.AppConstants.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(API_PATH + API_VERSION + AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<LoginResDTO> auth(@RequestBody LoginDTO loginDTO) {
        log.info("POST {} attempt for username={}", API_PATH + API_VERSION + AUTH, loginDTO.getUsername());
        LoginResDTO login = authService.login(loginDTO);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        log.info("POST {}/register attempt for username={}", API_PATH + API_VERSION + AUTH, registerDTO.getUsername());
        authService.register(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
