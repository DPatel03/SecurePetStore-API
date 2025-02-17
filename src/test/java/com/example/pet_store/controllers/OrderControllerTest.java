package com.example.pet_store.controllers;

import com.example.pet_store.models.Order;
import com.example.pet_store.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testPlaceOrder() throws Exception {
        Order order = new Order();
        order.setId(1);

        when(orderService.placeOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/store/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"petId\":10,\"quantity\":2,\"status\":\"pending\",\"complete\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andDo(print());

        verify(orderService, times(1)).placeOrder(any(Order.class));
    }

    @Test
    void testGetOrderById() throws Exception {
        Order order = new Order();
        order.setId(1);

        when(orderService.getOrderById(1)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/store/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andDo(print());

        verify(orderService, times(1)).getOrderById(1);
    }

    @Test
    void testGetOrderById_NotFound() throws Exception {
        when(orderService.getOrderById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/store/order/1"))
                .andExpect(status().isOk()) // Still returns 200 but with an empty body
                .andDo(print());

        verify(orderService, times(1)).getOrderById(1);
    }

    @Test
    void testDeleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder(1);

        mockMvc.perform(delete("/store/order/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order deleted successfully"))
                .andDo(print());

        verify(orderService, times(1)).deleteOrder(1);
    }

    @Test
    void testGetInventory() throws Exception {
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("shipped", 5);
        inventory.put("pending", 3);

        when(orderService.getInventoryByStatus()).thenReturn(inventory);

        mockMvc.perform(get("/store/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipped").value(5))
                .andExpect(jsonPath("$.pending").value(3))
                .andDo(print());

        verify(orderService, times(1)).getInventoryByStatus();
    }

    @Test
    void testGetAllOrders() throws Exception {
        List<Order> orders = Arrays.asList(new Order(), new Order());

        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/store/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());

        verify(orderService, times(1)).getAllOrders();
    }
}
