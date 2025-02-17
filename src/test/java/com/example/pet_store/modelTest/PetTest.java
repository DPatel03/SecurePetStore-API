package com.example.pet_store.modelTest;

import com.example.pet_store.models.Category;
import com.example.pet_store.models.Pet;
import com.example.pet_store.models.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PetTest {

    @Test
    void testPetGettersAndSetters() {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Buddy");
        pet.setStatus("available");
        pet.setPhotoUrl(List.of("photo1.jpg", "photo2.jpg"));

        Category category = new Category();
        category.setId(10);
        category.setName("Dog");

        Tag tag = new Tag();
        tag.setId(100);
        tag.setName("Friendly");

        pet.setCategory(category);
        pet.setTags(List.of(tag));

        assertEquals(1, pet.getId());
        assertEquals("Buddy", pet.getName());
        assertEquals("available", pet.getStatus());
        assertEquals(2, pet.getPhotoUrl().size());
        assertEquals(10, pet.getCategory().getId());
        assertEquals("Dog", pet.getCategory().getName());
        assertEquals(100, pet.getTags().get(0).getId());
        assertEquals("Friendly", pet.getTags().get(0).getName());
    }
}