package com.example.phonebook.service.impl;

import com.example.phonebook.dto.LoginDTO;
import com.example.phonebook.dto.LoginResDTO;
import com.example.phonebook.dto.RegisterDTO;
import com.example.phonebook.entity.User;
import com.example.phonebook.entity.enums.RoleName;
import com.example.phonebook.repository.RoleRepository;
import com.example.phonebook.repository.UserRepository;
import com.example.phonebook.security.service.JwtService;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    LoginDTO loginDTO;
    RegisterDTO registerDTO;

    @BeforeEach
    void setUp() {
        loginDTO = new LoginDTO("user", "password");

        registerDTO = new RegisterDTO(
                "user",
                "password",
                "password",
                "First",
                "Last"
        );
    }

    @Test
    void login_success() {
        Mockito.when(jwtService.generateToken("user"))
                .thenReturn("token");

        LoginResDTO login = authService.login(loginDTO);

        Assertions.assertNotNull(login);
        Assertions.assertEquals("token", login.getToken());

        Mockito.verify(authenticationManager)
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(jwtService).generateToken("user");
    }

    @Test
    void login_fail() {
        Mockito.doThrow(new AuthenticationServiceException("Bad credentials"))
                .when(authenticationManager)
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));

        Assertions.assertThrows(AuthenticationServiceException.class,
                () -> authService.login(loginDTO));
        Mockito.verify(authenticationManager)
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void register() {
        Mockito.when(userRepository.existsByUsername(registerDTO.getUsername()))
                .thenReturn(false);
        Mockito.when(passwordEncoder.encode(registerDTO.getPassword()))
                .thenReturn("encodedPassword");
        Mockito.when(roleRepository.findByRole(RoleName.USER))
                .thenReturn(List.of());

        authService.register(registerDTO);

        Mockito.verify(userRepository).save(Mockito.any(User.class));
        Mockito.verify(passwordEncoder).encode("password");
    }

    @Test
    void register_passwordNotMatch() {
        RegisterDTO wrongRegisterDTO = new RegisterDTO("user", "password", "wrong", "firstName", "lastName");

        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> authService.register(wrongRegisterDTO));

        Mockito.verifyNoInteractions(userRepository);
        Mockito.verifyNoInteractions(passwordEncoder);
    }

    @Test
    void register_userNameExists() {
        Mockito.when(userRepository.existsByUsername(registerDTO.getUsername()))
                .thenReturn(true);

        Assertions.assertThrows(EntityExistsException.class,
                () -> authService.register(registerDTO));

        Mockito.verify(userRepository).existsByUsername(registerDTO.getUsername());
        Mockito.verifyNoMoreInteractions(userRepository);
        Mockito.verifyNoInteractions(passwordEncoder);
    }
}