package com.example.pet_store.configTest;


import com.example.pet_store.config.JwtFilter;
import com.example.pet_store.service.JWTService;
import com.example.pet_store.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.mockito.Mockito.*;

class JWTFilterTest {

    @InjectMocks
    private JwtFilter jwtFilter;

    @Mock
    private JWTService jwtService;

    @Mock
    private ApplicationContext context;

    @Mock
    private MyUserDetailsService myUserDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
        String token = "validToken";
        String username = "Deep";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUserName(token)).thenReturn(username);
        when(context.getBean(MyUserDetailsService.class)).thenReturn(myUserDetailsService);
        when(myUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.validateToken(token, userDetails)).thenReturn(true);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
        when(jwtService.extractUserName("invalidToken")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_NoToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }
}