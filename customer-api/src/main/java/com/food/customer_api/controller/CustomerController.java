package com.food.customer_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @GetMapping
    public String getCustomers() {
        return "Customer Service Running";
    }

    @GetMapping("/{id}")
    public String getCustomer(@PathVariable int id) {
        return "Customer ID : " + id;
    }

    @PostMapping
    public String addCustomer() {
        return "Customer Registered Successfully";
    }
}