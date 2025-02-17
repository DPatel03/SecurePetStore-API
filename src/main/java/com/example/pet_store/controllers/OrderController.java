package com.example.pet_store.controllers;

import com.example.pet_store.models.Order;
import com.example.pet_store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    @PostMapping("/order")
    public Order placeOrder(@RequestBody Order order) {

        return orderService.placeOrder(order);
    }

    @GetMapping("/order/{orderId}")
    public Optional<Order> getOrderById(@PathVariable int orderId) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/order/{orderId}")
    public String deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
        return "Order deleted successfully";
    }
    @GetMapping("/inventory")
    public Map<String, Integer> getInventory() {
        return orderService.getInventoryByStatus();
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }


}