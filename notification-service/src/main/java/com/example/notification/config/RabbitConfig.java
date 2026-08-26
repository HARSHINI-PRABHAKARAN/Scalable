package com.example.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public TopicExchange ordersExchange() {
        return new TopicExchange("orders.exchange", true, false);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue("order.notification.queue", true);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange ordersExchange) {
        return BindingBuilder.bind(notificationQueue).to(ordersExchange).with("order.created");
    }
}
