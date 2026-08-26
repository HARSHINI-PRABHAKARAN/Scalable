package com.food.restaurant_service.controller;


import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/restaurants")
public class RestaurantController {


@GetMapping("/{id}")
public Map<String,Object> getRestaurant(
        @PathVariable int id
){

return Map.of(

"id",id,
"name","Dominos",
"location","Chennai"

);

}

}