package com.bekzod.phonebook.service.impl;

import com.bekzod.phonebook.dto.LoginDTO;
import com.bekzod.phonebook.dto.LoginResDTO;
import com.bekzod.phonebook.dto.RegisterDTO;
import com.bekzod.phonebook.entity.User;
import com.bekzod.phonebook.entity.enums.RoleName;
import com.bekzod.phonebook.repository.RoleRepository;
import com.bekzod.phonebook.repository.UserRepository;
import com.bekzod.phonebook.security.JwtService;
import com.bekzod.phonebook.service.AuthService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public LoginResDTO login(LoginDTO loginDTO) {
        var auth = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        try {
            authenticationManager.authenticate(auth);
            log.info("User {} logged in successfully", loginDTO.getUsername());
        } catch (AuthenticationException e) {
            log.warn("Authentication failed for username={}", loginDTO.getUsername());
            throw new AuthenticationServiceException(e.getMessage());
        }
        String token = jwtService.generateToken(loginDTO.getUsername());
        return new LoginResDTO(token);
    }

    @Override
    public void register(RegisterDTO registerDTO) {

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            log.warn("Password mismatch for username={}", registerDTO.getUsername());
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            log.warn("Username {} already exists", registerDTO.getUsername());
            throw new EntityExistsException("Username already exists");
        }

        User user = User.builder()
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .roles(roleRepository.findByRole(RoleName.USER))
                .build();
        userRepository.save(user);
        log.info("User {} registered successfully", registerDTO.getUsername());
    }
}
