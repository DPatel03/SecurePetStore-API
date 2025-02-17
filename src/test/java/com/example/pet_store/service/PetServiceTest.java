package com.example.pet_store.service;

import com.example.pet_store.models.Pet;
import com.example.pet_store.repository.PetRepository;
import com.example.pet_store.repository.CategoryRepository;
import com.example.pet_store.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private PetService petService;

    private Pet pet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a mock pet
        pet = new Pet();
        pet.setId(1);
        pet.setName("Buddy");
    }

    @Test
    void testSavePet() {
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet savedPet = petService.savePet(pet);

        assertNotNull(savedPet);
        assertEquals("Buddy", savedPet.getName());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void testGetPetById() {
        when(petRepository.findById(1)).thenReturn(Optional.of(pet));

        Pet foundPet = petService.getPetById(1);

        assertNotNull(foundPet);
        assertEquals(1, foundPet.getId());
        verify(petRepository, times(1)).findById(1);
    }

    @Test
    void testGetPetById_NotFound() {
        when(petRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> petService.getPetById(1));
        assertEquals("Pet not found", exception.getMessage());
    }

    @Test
    void testUpdatePet() {
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet updatedPet = petService.updatePet(pet);

        assertNotNull(updatedPet);
        assertEquals("Buddy", updatedPet.getName());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void testDeletePet() {
        doNothing().when(petRepository).deleteById(1);

        petService.deletePet(1);

        verify(petRepository, times(1)).deleteById(1);
    }
}
