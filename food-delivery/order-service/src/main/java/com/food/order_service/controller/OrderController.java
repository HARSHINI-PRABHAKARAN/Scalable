package com.food.order_service.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@RestController
@RequestMapping("/orders")
public class OrderController {


private final RestTemplate restTemplate;


public OrderController(RestTemplate restTemplate){

this.restTemplate=restTemplate;

}



@GetMapping("/{id}")
public Map<String,Object> createOrder(
        @PathVariable int id
){


String customerUrl =
"http://localhost:8081/customers/1";


String restaurantUrl =
"http://localhost:8082/restaurants/1";



Map customer =
restTemplate.getForObject(
        customerUrl,
        Map.class
);



Map restaurant =
restTemplate.getForObject(
        restaurantUrl,
        Map.class
);



return Map.of(

"orderId",id,

"customer",customer,

"restaurant",restaurant,

"status","ORDER_PLACED"

);


}



}