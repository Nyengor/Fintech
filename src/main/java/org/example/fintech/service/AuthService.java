package org.example.fintech.service;

import org.example.fintech.config.JwtUtil;
import org.example.fintech.dto.LoginRequest;
import org.example.fintech.dto.LoginResponse;
import org.example.fintech.dto.RegisterRequest;
import org.example.fintech.dto.RegisterResponse;
import org.example.fintech.entity.AuthEntity;
import org.example.fintech.event.AuditEvent;
import org.example.fintech.repository.AuthRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ApplicationEventPublisher eventPublisher;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil, ApplicationEventPublisher eventPublisher) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.eventPublisher = eventPublisher;
    }

    public RegisterResponse register(String ipAddress, RegisterRequest request) {
        try {
            if (authRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }

            AuthEntity authEntity = new AuthEntity();
            authEntity.setFirstName(request.getFirstName());
            authEntity.setLastName(request.getLastName());
            authEntity.setEmail(request.getEmail());
            authEntity.setPassword(passwordEncoder.encode(request.getPassword()));


            authRepository.save(authEntity);

            eventPublisher.publishEvent(new AuditEvent(
                    "SUCCESS",
                    ipAddress,
                    "User registered successfully",
                    "Auth",
                    "N/A",
                    request.getEmail(),
                    LocalDateTime.now()
                    ));


            return new RegisterResponse("User registered");

        } catch (Exception e) {
            eventPublisher.publishEvent(new AuditEvent(
                    "FAILED",
                    ipAddress,
                    "Registration failed" + e.getMessage(),
                    "Auth",
                    "N/A",
                    request.getEmail(),
                    LocalDateTime.now()
            ));
            throw e;
        }
    }

    public LoginResponse login(String ipAddress, LoginRequest request) {
        try {
//        Use auth manager which the use auth provider to authenticate
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                    )
            );

            eventPublisher.publishEvent(new AuditEvent(
                            "SUCCESS",
                            ipAddress,
                            "User logged in successfully",
                            "Auth",
                            "N/A",
                            request.getEmail(),
                            LocalDateTime.now()
            ));

            String token = jwtUtil.generateToken(request.getEmail());

            return new LoginResponse(token);

        } catch (AuthenticationException e) {
            // FAILURE EVENT (Bad credentials, locked account, etc.)
            eventPublisher.publishEvent(new AuditEvent(
                    "FAILED",
                    ipAddress,
                    "Login failed: Invalid credentials",
                    "Auth",
                    "N/A",
                    request.getEmail(),
                    LocalDateTime.now()
            ));
            throw e;

        } catch (Exception e) {
            // ANY OTHER GENERAL SYSTEM FAILURE
            eventPublisher.publishEvent(new AuditEvent(
                    "FAILED",
                    ipAddress,
                    "Login failed: " + e.getMessage(),
                    "Auth",
                    "N/A",
                    request.getEmail(),
                    LocalDateTime.now()
            ));
            throw e;
        }

    }
}
