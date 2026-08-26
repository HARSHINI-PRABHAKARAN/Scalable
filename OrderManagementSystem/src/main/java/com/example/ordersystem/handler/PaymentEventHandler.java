package com.example.ordersystem.handler;

import com.example.ordersystem.events.PaymentCompletedEvent;
import com.example.ordersystem.model.Order;
import com.example.ordersystem.service.IdempotentService;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventHandler {

    private final IdempotentService idempotentService;

    public PaymentEventHandler(IdempotentService idempotentService) {
        this.idempotentService = idempotentService;
    }

    public void handlePaymentCompleted(Order order,
                                       PaymentCompletedEvent event) {

        if (idempotentService.isProcessed(event.getEventId())) {

            System.out.println("Duplicate Event Ignored : " + event.getEventId());
            return;
        }

        System.out.println("\n===== PaymentCompletedEvent Received =====");
        System.out.println("Order ID : " + event.getOrderId());
        System.out.println("Payment Status : " + event.getPaymentStatus());

        order.setStatus("PAID");

        System.out.println("Order Status Updated To : " + order.getStatus());
        System.out.println("==========================================\n");
    }

}