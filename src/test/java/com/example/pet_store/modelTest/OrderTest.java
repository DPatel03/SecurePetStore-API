package com.example.pet_store.modelTest;


import com.example.pet_store.models.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testOrderGettersAndSetters() {
        Order order = new Order();
        order.setId(1);
        order.setPetId(5);
        order.setQuantity(2);
        order.setShipDate("2025-03-01");
        order.setStatus("shipped");

        assertEquals(1, order.getId());
        assertEquals(5, order.getPetId());
        assertEquals(2, order.getQuantity());
        assertEquals("2025-03-01", order.getShipDate());
        assertEquals("shipped", order.getStatus());
    }
}