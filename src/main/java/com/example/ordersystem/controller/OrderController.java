package com.example.ordersystem.controller;

import com.example.ordersystem.model.Order;
import com.example.ordersystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {

        return orderService.createOrder(order);

    }

}