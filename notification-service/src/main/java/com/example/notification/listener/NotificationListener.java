package com.example.notification.listener;

import com.example.common.model.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    @RabbitListener(queues = "order.notification.queue")
    public void handleOrderCreated(Order order) {
        System.out.println("[NotificationService] Received OrderCreated: " + order.getId());
        System.out.println("[NotificationService] Sent notification for order " + order.getId());
    }
}
