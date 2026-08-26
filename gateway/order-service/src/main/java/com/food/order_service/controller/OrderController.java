package com.food.order_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping
    public String home() {
        return "Order Service Running";
    }

    @GetMapping("/{id}")
    public String order(@PathVariable int id) {

        return "Order ID : " + id;

    }

}