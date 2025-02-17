package com.example.pet_store.controllers;

import com.example.pet_store.models.Tag;
import com.example.pet_store.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TagControllerTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagController tagController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTag() {
        // Create a test tag
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Dog");

        // Tell the mock what to return when save is called
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        // Call the controller method
        Tag result = tagController.addTag(tag);

        // Check that the result has the expected values
        assertEquals(1, result.getId());
        assertEquals("Dog", result.getName());

        // Verify that the repository's save method was called once
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    public void testGetTagById() {
        // Create a test tag
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Dog");

        // Tell the mock what to return when findById is called
        when(tagRepository.findById(1)).thenReturn(Optional.of(tag));

        // Call the controller method
        Tag result = tagController.getTagById(1);

        // Check that the result has the expected values
        assertEquals(1, result.getId());
        assertEquals("Dog", result.getName());

        // Verify that the repository's findById method was called once
        verify(tagRepository, times(1)).findById(1);
    }


    @Test
    public void testDeleteTag() {
        // Call the controller method
        tagController.deleteTag(1);

        // Verify that the repository's deleteById method was called once with ID 1
        verify(tagRepository, times(1)).deleteById(1);
    }
}