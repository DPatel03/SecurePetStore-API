package com.example.pet_store.modelTest;

import com.example.pet_store.models.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagTest {
    @Test
    void testTagModel(){
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Cute");

        assertEquals(1, tag.getId());
        assertEquals("Cute", tag.getName());

    }
}