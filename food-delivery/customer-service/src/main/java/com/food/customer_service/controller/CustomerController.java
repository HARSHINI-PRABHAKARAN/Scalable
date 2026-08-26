package com.food.customer_service.controller;


import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/customers")
public class CustomerController {


    @GetMapping("/{id}")
    public Map<String,Object> getCustomer(
            @PathVariable int id
    ){

        return Map.of(
                "id",id,
                "name","Harshini",
                "email","harshini@gmail.com"
        );
    }

}