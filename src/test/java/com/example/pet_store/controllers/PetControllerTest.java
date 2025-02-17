package com.example.pet_store.controllers;

import com.example.pet_store.models.Pet;
import com.example.pet_store.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    private Pet pet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

        pet = new Pet();
        pet.setId(1);
        pet.setName("Buddy");
        pet.setStatus("Available");
    }

    @Test
    void testAddPet() throws Exception {
        when(petService.savePet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Buddy\", \"status\": \"Available\"}"))
                .andExpect(status().isOk());

        verify(petService, times(1)).savePet(any(Pet.class));
    }

    @Test
    void testGetPetById() throws Exception {
        when(petService.getPetById(1)).thenReturn(pet);

        mockMvc.perform(get("/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Buddy"));

        verify(petService, times(1)).getPetById(1);
    }

    @Test
    void testUpdatePet() throws Exception {
        when(petService.updatePet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(put("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Buddy\", \"status\": \"Available\"}"))
                .andExpect(status().isOk());

        verify(petService, times(1)).updatePet(any(Pet.class));
    }

    @Test
    void testDeletePet() throws Exception {
        doNothing().when(petService).deletePet(1);

        mockMvc.perform(delete("/pet/1"))
                .andExpect(status().isOk());

        verify(petService, times(1)).deletePet(1);
    }

    @Test
    void testFindPetsByStatus() throws Exception {
        when(petService.getPetsByStatus(anyList())).thenReturn(List.of(pet));

        mockMvc.perform(get("/pet/findByStatus")
                        .param("status", "Available"))
                .andExpect(status().isOk());

        verify(petService, times(1)).getPetsByStatus(anyList());
    }
}
