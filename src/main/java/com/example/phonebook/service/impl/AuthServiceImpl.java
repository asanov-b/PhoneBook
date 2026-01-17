package com.example.phonebook.service.impl;

import com.example.phonebook.dto.LoginDTO;
import com.example.phonebook.dto.LoginResDTO;
import com.example.phonebook.dto.RegisterDTO;
import com.example.phonebook.entity.User;
import com.example.phonebook.entity.enums.RoleName;
import com.example.phonebook.repository.RoleRepository;
import com.example.phonebook.repository.UserRepository;
import com.example.phonebook.security.service.JwtService;
import com.example.phonebook.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        } catch (AuthenticationException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
        String token = jwtService.generateToken(loginDTO.getUsername());
        return new LoginResDTO(token);
    }

    @SneakyThrows
    @Override
    public void register(RegisterDTO registerDTO) {

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        User user = User.builder()
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .roles(roleRepository.findByRole(RoleName.USER))
                .build();
        userRepository.save(user);
    }
}
