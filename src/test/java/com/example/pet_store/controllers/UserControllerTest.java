package com.example.pet_store.controllers;

import com.example.pet_store.models.User;
import com.example.pet_store.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setRole("CUSTOMER");
    }

    @Test
    void testRegisterUser() throws Exception {
        when(userService.register(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password\"}"))
                .andExpect(status().isOk());

        verify(userService, times(1)).register(any(User.class));
    }

    @Test
    void testGetUserByUsername() throws Exception {
        when(userService.getUserByUsername("testuser")).thenReturn(user);

        mockMvc.perform(get("/user/testuser"))
                .andExpect(status().isOk());

        verify(userService, times(1)).getUserByUsername("testuser");
    }

    @Test
    void testUpdateUser() throws Exception {
        when(userService.updateUser(eq("testuser"), any(User.class))).thenReturn(user);

        mockMvc.perform(put("/user/testuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Updated\", \"lastName\": \"User\"}"))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUser(eq("testuser"), any(User.class));
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser("testuser");

        mockMvc.perform(delete("/user/testuser"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser("testuser");
    }

    @Test
    void testLoginUser() throws Exception {
        when(userService.loginUser(any(User.class))).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password\"}"))
                .andExpect(status().isOk());

        verify(userService, times(1)).loginUser(any(User.class));
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(user, new User());
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk());

        verify(userService, times(1)).getAllUsers();
    }
}
