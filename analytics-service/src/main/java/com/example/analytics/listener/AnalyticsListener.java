package com.example.analytics.listener;

import com.example.common.model.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsListener {
    @RabbitListener(queues = "order.analytics.queue")
    public void handleOrderCreated(Order order) {
        System.out.println("[AnalyticsService] Received OrderCreated: " + order.getId());
        System.out.println("[AnalyticsService] Recorded metrics for order " + order.getId());
    }
}
