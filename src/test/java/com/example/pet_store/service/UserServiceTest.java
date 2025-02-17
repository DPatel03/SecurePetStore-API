package com.example.pet_store.service;

import com.example.pet_store.models.User;
import com.example.pet_store.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authManager;

    @InjectMocks
    private UserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.register(user);

        assertNotNull(savedUser);
        assertEquals("testuser", savedUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserByUsername() {
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = userService.getUserByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User();
        existingUser.setUsername("testuser");

        User updatedDetails = new User();
        updatedDetails.setFirstName("John");

        when(userRepository.findByUsername("testuser")).thenReturn(existingUser);
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.updateUser("testuser", updatedDetails);

        assertNotNull(updatedUser);
        assertEquals("John", updatedUser.getFirstName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        doNothing().when(userRepository).delete(user);

        userService.deleteUser("testuser");

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testLoginUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken("testuser")).thenReturn("token");

        String token = userService.loginUser(user);

        assertEquals("token", token);
        verify(authManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }
}
