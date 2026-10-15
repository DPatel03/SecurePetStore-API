package com.example.pet_store.modelTest;

import com.example.pet_store.models.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void testUserModel() {
        User user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("A@123");
        user.setPhone("1234567890");
        user.setUserStatus(1);
        user.setRole("CUSTOMER");

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(user.getPassword()).isEqualTo("A@123");
        assertThat(user.getPhone()).isEqualTo("1234567890");
        assertThat(user.getUserStatus()).isEqualTo(1);
        assertThat(user.getRole()).isEqualTo("CUSTOMER");
    }
}
