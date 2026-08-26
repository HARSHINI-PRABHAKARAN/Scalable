package com.example.payment.listener;

import com.example.common.model.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {
    @RabbitListener(queues = "order.payment.queue")
    public void handleOrderCreated(Order order) {
        System.out.println("[PaymentService] Received OrderCreated: " + order.getId());
        // simulate payment processing
        System.out.println("[PaymentService] Processed payment for order " + order.getId());
    }
}
