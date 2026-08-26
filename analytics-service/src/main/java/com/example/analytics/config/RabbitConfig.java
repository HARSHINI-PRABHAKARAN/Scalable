package com.example.analytics.config;

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
    public Queue analyticsQueue() {
        return new Queue("order.analytics.queue", true);
    }

    @Bean
    public Binding analyticsBinding(Queue analyticsQueue, TopicExchange ordersExchange) {
        return BindingBuilder.bind(analyticsQueue).to(ordersExchange).with("order.created");
    }
}
