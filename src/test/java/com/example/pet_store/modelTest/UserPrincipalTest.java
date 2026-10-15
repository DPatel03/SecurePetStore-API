package com.example.pet_store.modelTest;


import com.example.pet_store.models.User;
import com.example.pet_store.models.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserPrincipalTest {

    @Test
    void testUserPrincipalMethods() {
        User mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn("Deep");
        when(mockUser.getPassword()).thenReturn("D@123");
        when(mockUser.getRole()).thenReturn("MANAGER");

        UserPrincipal userPrincipal = new UserPrincipal(mockUser);

        assertEquals("Deep", userPrincipal.getUsername());
        assertEquals("D@123", userPrincipal.getPassword());

        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
        assertFalse(authorities.isEmpty());
        assertEquals("ROLE_MANAGER", authorities.iterator().next().getAuthority());

        assertTrue(userPrincipal.isAccountNonExpired());
        assertTrue(userPrincipal.isAccountNonLocked());
        assertTrue(userPrincipal.isCredentialsNonExpired());
        assertTrue(userPrincipal.isEnabled());
    }
}
