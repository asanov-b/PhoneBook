package com.example.phonebook.security.service;

import com.example.phonebook.entity.Role;
import com.example.phonebook.entity.User;
import com.example.phonebook.entity.enums.RoleName;
import com.example.phonebook.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final UserRepository userRepository;

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(String username) {
        User user = userRepository.findByUsername(username);
        return "Bearer " + Jwts.builder()
                .subject(username)
                .claim("roles", user.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.joining(",")))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(signKey())
                .compact();
    }

    public SecretKey signKey() {
        return Keys.hmacShaKeyFor("PhoneNumberBookSecretKey/PhoneNumberBookSecretKey".getBytes());
    }

    public boolean isValid(String token) {
        try {
            Jwts
                    .parser()
                    .verifyWith(signKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    public User getUserObject(String token) {
        Claims payload = Jwts.parser()
                .verifyWith(signKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = payload.getSubject();
        String roles = payload.get("roles", String.class);
        List<Role> roles1 = Arrays.stream(roles.split(",")).map(r -> new Role(RoleName.valueOf(r))).toList();
        return new User(username, roles1);
    }
}
