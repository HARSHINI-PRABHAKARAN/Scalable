package com.food.order_api.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping
    public String getOrders() {
        return "Order Service Running";
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable int id) {
        return "Order ID : " + id;
    }

    @PostMapping
    public String placeOrder() {
        return "Order Placed Successfully";
    }
}