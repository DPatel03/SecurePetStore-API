package com.example.pet_store.service;


import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTServiceTest {

    @InjectMocks
    private JWTService jwtService;

    @Mock
    private Claims claims;

    @BeforeEach
    void setUp() {
        jwtService = new JWTService();  // Initialize the service
    }

    @Test
    void testGenerateToken() {
        String username = "testuser";

        String token = jwtService.generateToken(username);

        assertNotNull(token, "The token should not be null");
    }

    @Test
    void testExtractUserName() {
        String username = "testuser";
        String token = jwtService.generateToken(username);

        String extractedUsername = jwtService.extractUserName(token);

        assertEquals(username, extractedUsername, "The extracted username should match the original one");
    }

    @Test
    void testValidateToken() {
        String username = "testuser";
        String token = jwtService.generateToken(username);

        boolean isValid = jwtService.validateToken(token, new org.springframework.security.core.userdetails.User(username, "", new ArrayList<>()));

        assertTrue(isValid, "The token should be valid");
    }

    @Test
    void testIsTokenExpired() {
        String token = jwtService.generateToken("testuser");

        boolean isExpired = jwtService.isTokenExpired(token);

        assertFalse(isExpired, "The token should not be expired immediately after creation");
    }
}
