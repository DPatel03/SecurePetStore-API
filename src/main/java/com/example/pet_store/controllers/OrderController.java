package com.example.pet_store.controllers;

import com.example.pet_store.models.Order;
import com.example.pet_store.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    @PostMapping("/order")
    public Order placeOrder(@RequestBody Order order) {

        return orderService.placeOrder(order);
    }

    @GetMapping("/order/{orderId}")
    public Optional<Order> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/order/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return "Order deleted successfully";
    }
    @GetMapping("/inventory")
    public Map<String, Integer> getInventory() {
        return orderService.getInventoryByStatus();
    }

}