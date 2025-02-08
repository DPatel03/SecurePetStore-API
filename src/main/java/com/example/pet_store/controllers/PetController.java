package com.example.pet_store.controllers;

import com.example.pet_store.models.Category;
import com.example.pet_store.models.Pet;
import com.example.pet_store.PetService;
import com.example.pet_store.models.Tag;
import com.example.pet_store.repository.CategoryRepository;
import com.example.pet_store.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

    @RestController
    @RequestMapping("/pet")
    public class PetController {

        @Autowired
        private CategoryRepository categoryRepository;

        @Autowired
        private TagRepository tagRepository;

        @Autowired
        private PetService petService;

        @PostMapping
        public Pet addPet(@RequestBody Pet petRequest) {
            // Fetch the Category by categoryId
            Category category = categoryRepository.findById(petRequest.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            // Fetch the Tags by tagIds
            List<Tag> tags = tagRepository.findAllById(petRequest.getTags().stream()
                    .map(Tag::getId)
                    .collect(Collectors.toList()));

            // Set the fetched Category and Tags to the Pet entity
            petRequest.setCategory(category);
            petRequest.setTags(tags);

            // Save and return the Pet
            return petService.savePet(petRequest);
        }

    // GET: Retrieve a pet by its ID
    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }


    // GET: Retrieve pets by status (optional query parameter)
    @GetMapping("/findByStatus")
    public List<Pet> getPetsByStatus(@RequestParam String status) {
        return petService.getPetsByStatus(status);
    }

    // PUT: Update an existing pet
    @PutMapping
    public Pet updatePet(@RequestBody Pet pet) {
        return petService.updatePet(pet);
    }

    // DELETE: Delete a pet by ID
    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable Long id) {
        petService.deletePet(id);
    }
}