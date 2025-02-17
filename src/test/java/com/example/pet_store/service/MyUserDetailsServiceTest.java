package com.example.pet_store.service;

import com.example.pet_store.models.User;
import com.example.pet_store.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyUserDetailsServiceTest {

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
    }

    @Test
    void testLoadUserByUsername() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        // When
        UserDetails userDetails = myUserDetailsService.loadUserByUsername("testuser");

        // Then
        assertNotNull(userDetails, "The user details should not be null");
        assertEquals("testuser", userDetails.getUsername(), "The username should match");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Given
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        // When / Then
        assertThrows(UsernameNotFoundException.class, () -> myUserDetailsService.loadUserByUsername("nonexistent"));
    }
}
