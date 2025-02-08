package com.example.pet_store;

import com.example.pet_store.models.Pet;
import com.example.pet_store.repository.CategoryRepository;
import com.example.pet_store.repository.PetRepository;
import com.example.pet_store.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
    }


    public Pet updatePet(Pet pet) {

        return petRepository.save(pet);
    }

    public void deletePet(Long id) {

        petRepository.deleteById(id);
    }


    public List<Pet> getPetsByStatus(String status) {
        return petRepository.findByStatus(status);

    }

}