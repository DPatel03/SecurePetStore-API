package com.example.pet_store.config;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("user/register", "user/login").permitAll()

                        // CUSTOMER: Can only view pets and orders
                        .requestMatchers(HttpMethod.GET, "/pet/{id}").hasAnyRole("CUSTOMER", "EMPLOYEE", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/store/order/{orderId}").hasAnyRole("CUSTOMER", "EMPLOYEE", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/tag/{id}").hasAnyRole("CUSTOMER", "EMPLOYEE", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/category/{id}").hasAnyRole("CUSTOMER", "EMPLOYEE", "MANAGER")

                        // EMPLOYEE: Can update pets and view inventory
                        .requestMatchers(HttpMethod.POST, "/pet").hasAnyRole("EMPLOYEE", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/order").hasAnyRole("EMPLOYEE", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/tag").hasAnyRole("EMPLOYEE", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/category").hasAnyRole("EMPLOYEE", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/store/order").hasAnyRole("EMPLOYEE", "MANAGER")




                        .requestMatchers(HttpMethod.GET, "/store/inventory").hasAnyRole("EMPLOYEE", "MANAGER")

                        // EMPLOYEE: Can retrieve user details but not update or delete
                        .requestMatchers(HttpMethod.GET, "/user/{username}").hasAnyRole("EMPLOYEE", "MANAGER")

                        // MANAGER: Can delete and update anything
                        .requestMatchers(HttpMethod.DELETE, "/pet/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/store/order/{orderId}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/user/{username}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/tag/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/category/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/user/all").hasRole("MANAGER")


                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        provider.setUserDetailsService(userDetailsService);


        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }
}