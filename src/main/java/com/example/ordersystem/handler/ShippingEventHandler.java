package com.example.ordersystem.handler;

import com.example.ordersystem.events.ShippingAssignedEvent;
import com.example.ordersystem.model.Order;
import org.springframework.stereotype.Component;

@Component
public class ShippingEventHandler {

    public void handleShipping(Order order,
                               ShippingAssignedEvent event) {

        System.out.println("\n===== ShippingAssignedEvent Received =====");
        System.out.println("Order ID : " + event.getOrderId());
        System.out.println("Shipping Status : " + event.getShippingStatus());

        order.setStatus("COMPLETED");

        System.out.println("Order Status Updated To : " + order.getStatus());
        System.out.println("==========================================\n");
    }

}