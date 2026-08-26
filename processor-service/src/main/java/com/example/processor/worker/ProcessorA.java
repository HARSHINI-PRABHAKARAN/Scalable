package com.example.processor.worker;

import com.example.common.model.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProcessorA {
    @RabbitListener(queues = "order.processing.queue")
    public void process(Order order) {
        System.out.println("[ProcessorA] Received: " + order.getId());
        // Simulate intermittent failure for half of messages
        if (order.getId().hashCode() % 2 == 0) {
            System.out.println("[ProcessorA] Simulating failure for " + order.getId());
            throw new RuntimeException("simulated processing error");
        }
        System.out.println("[ProcessorA] Processed " + order.getId());
    }
}
