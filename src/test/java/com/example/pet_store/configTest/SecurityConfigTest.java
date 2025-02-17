package com.example.pet_store.configTest;


import com.example.pet_store.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class SecurityConfigTest {

    private  UserDetailsService userDetailsService = mock(UserDetailsService.class);
    private  SecurityConfig securityConfig = new SecurityConfig();

    @Test
    void testAuthenticationProvider() {
        AuthenticationProvider provider = securityConfig.authenticationProvider();
        assertNotNull(provider);
    }

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationConfiguration config = mock(AuthenticationConfiguration.class);
        Mockito.when(config.getAuthenticationManager()).thenReturn(mock(AuthenticationManager.class));

        AuthenticationManager authenticationManager = securityConfig.authenticationManager(config);
        assertNotNull(authenticationManager);
    }

    @Test
    void testPasswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String encodedPassword = encoder.encode("D@123");
        assertNotNull(encodedPassword);
    }
}