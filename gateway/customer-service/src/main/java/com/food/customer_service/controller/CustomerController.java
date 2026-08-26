package com.food.customer_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping
    public String home() {
        return "Customer Service Running";
    }

    @GetMapping("/{id}")
    public String customer(@PathVariable int id) {

        return "Customer ID : " + id;

    }

}