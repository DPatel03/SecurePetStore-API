package com.example.pet_store.repository;

import com.example.pet_store.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    void deleteByUsername(String username);

}
