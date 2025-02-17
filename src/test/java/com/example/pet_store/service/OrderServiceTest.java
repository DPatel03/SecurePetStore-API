package com.example.pet_store.service;

import com.example.pet_store.models.Order;
import com.example.pet_store.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder() {
        Order order = new Order();
        order.setId(1);
        when(orderRepository.save(order)).thenReturn(order);

        Order savedOrder = orderService.placeOrder(order);

        assertNotNull(savedOrder);
        assertEquals(1, savedOrder.getId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetOrderById() {
        Order order = new Order();
        order.setId(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testDeleteOrder() {
        doNothing().when(orderRepository).deleteById(1);

        orderService.deleteOrder(1);

        verify(orderRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetInventoryByStatus() {
        Order order1 = new Order();
        order1.setStatus("shipped");
        order1.setQuantity(3);

        Order order2 = new Order();
        order2.setStatus("shipped");
        order2.setQuantity(2);

        Order order3 = new Order();
        order3.setStatus("pending");
        order3.setQuantity(5);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2, order3));

        Map<String, Integer> inventory = orderService.getInventoryByStatus();

        assertEquals(2, inventory.size());
        assertEquals(5, inventory.get("shipped"));
        assertEquals(5, inventory.get("pending"));
        verify(orderRepository, times(1)).findAll();
    }
}
