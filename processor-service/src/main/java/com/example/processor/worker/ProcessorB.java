package com.example.processor.worker;

import com.example.common.model.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProcessorB {
    @RabbitListener(queues = "order.processing.queue")
    public void process(Order order) {
        System.out.println("[ProcessorB] Received: " + order.getId());
        System.out.println("[ProcessorB] Processed " + order.getId());
    }
}
