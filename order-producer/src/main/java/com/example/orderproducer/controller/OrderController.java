package com.example.orderproducer.controller;

import com.example.common.model.Address;
import com.example.common.model.Order;
import com.example.common.model.OrderItem;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final RabbitTemplate rabbitTemplate;
    public OrderController(RabbitTemplate rabbitTemplate) { this.rabbitTemplate = rabbitTemplate; }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder() {
        Order order = new Order(UUID.randomUUID().toString(), "cust-123",
                List.of(new OrderItem("prod-1", 2), new OrderItem("prod-2",1)),
                new Address("1 Main St","City","12345"));

        rabbitTemplate.convertAndSend("orders.exchange", "order.created", order);
        return ResponseEntity.ok(order);
    }
}
