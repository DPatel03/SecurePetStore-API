package com.example.pet_store.repository;

import com.example.pet_store.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByStatusIn(List<String> status);
}