package com.fooddelivery.customerservice;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class CustomerController {


    @GetMapping("/customer/service-info")
    public Map<String,String> serviceInfo(){

        Map<String,String> response = new HashMap<>();

        response.put("serviceName",
                "Customer Service");

        response.put("description",
                "Handles customer registration and customer details");

        response.put("status",
                "Running");


        return response;
    }

}