package com.example.pet_store.modelTest;


import com.example.pet_store.models.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testCategoryGettersAndSetters() {
        Category category = new Category();
        category.setId(1);
        category.setName("Dogs");

        assertEquals(1, category.getId());
        assertEquals("Dogs", category.getName());
    }
}