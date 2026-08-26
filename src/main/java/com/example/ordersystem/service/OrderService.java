package com.example.ordersystem.service;

import com.example.ordersystem.events.OrderPlacedEvent;
import com.example.ordersystem.events.PaymentCompletedEvent;
import com.example.ordersystem.events.ShippingAssignedEvent;
import com.example.ordersystem.handler.PaymentEventHandler;
import com.example.ordersystem.handler.ShippingEventHandler;
import com.example.ordersystem.model.Order;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private final PaymentEventHandler paymentEventHandler;
    private final ShippingEventHandler shippingEventHandler;

    public OrderService(PaymentEventHandler paymentEventHandler,
                        ShippingEventHandler shippingEventHandler) {
        this.paymentEventHandler = paymentEventHandler;
        this.shippingEventHandler = shippingEventHandler;
    }

    public Order createOrder(Order order) {


        order.setStatus("CREATED");

        OrderPlacedEvent orderEvent = new OrderPlacedEvent(
                UUID.randomUUID().toString(),
                order.getOrderId(),
                order.getCustomerName(),
                order.getAmount()
        );

        publishOrderEvent(orderEvent);


        PaymentCompletedEvent paymentEvent = new PaymentCompletedEvent(
                UUID.randomUUID().toString(),
                order.getOrderId(),
                "SUCCESS"
        );


        paymentEventHandler.handlePaymentCompleted(order, paymentEvent);

        paymentEventHandler.handlePaymentCompleted(order, paymentEvent);


        ShippingAssignedEvent shippingEvent = new ShippingAssignedEvent(
                UUID.randomUUID().toString(),
                order.getOrderId(),
                "ASSIGNED"
        );

        shippingEventHandler.handleShipping(order, shippingEvent);

        return order;
    }

    private void publishOrderEvent(OrderPlacedEvent event) {

        System.out.println("\n=================================");
        System.out.println("Publishing OrderPlacedEvent");
        System.out.println("Event ID      : " + event.getEventId());
        System.out.println("Order ID      : " + event.getOrderId());
        System.out.println("Customer Name : " + event.getCustomerName());
        System.out.println("Amount        : " + event.getAmount());
        System.out.println("=================================\n");
    }
}