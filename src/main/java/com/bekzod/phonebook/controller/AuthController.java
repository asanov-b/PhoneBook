package com.bekzod.phonebook.controller;

import com.bekzod.phonebook.dto.LoginDTO;
import com.bekzod.phonebook.dto.LoginResDTO;
import com.bekzod.phonebook.dto.RegisterDTO;
import com.bekzod.phonebook.service.AuthService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bekzod.phonebook.utils.AppConstants.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(API_PATH + API_VERSION + AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<LoginResDTO> auth(@Valid @RequestBody LoginDTO loginDTO) {
        log.info("POST {} attempt for username={}", API_PATH + API_VERSION + AUTH, loginDTO.getUsername());
        LoginResDTO login = authService.login(loginDTO);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        log.info("POST {}/register attempt for username={}", API_PATH + API_VERSION + AUTH, registerDTO.getUsername());
        authService.register(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
