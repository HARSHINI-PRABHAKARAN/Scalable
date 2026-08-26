package com.example.processor.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.aop.Advice;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    public static final String ORDERS_EXCHANGE = "orders.exchange";
    public static final String PROCESSING_QUEUE = "order.processing.queue";
    public static final String PROCESSING_DLX = "order.processing.dlx";
    public static final String PROCESSING_DLQ = "order.processing.dlq";

    @Bean
    public TopicExchange ordersExchange() {
        return new TopicExchange(ORDERS_EXCHANGE, true, false);
    }

    @Bean
    public FanoutExchange processingDlx() { return new FanoutExchange(PROCESSING_DLX, true, false); }

    @Bean
    public Queue processingQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", PROCESSING_DLX);
        return new Queue(PROCESSING_QUEUE, true, false, false, args);
    }

    @Bean
    public Queue processingDlq() { return new Queue(PROCESSING_DLQ, true); }

    @Bean
    public Binding processingBinding(Queue processingQueue, TopicExchange ordersExchange) {
        return BindingBuilder.bind(processingQueue).to(ordersExchange).with("order.created");
    }

    @Bean
    public Binding dlqBinding(Queue processingDlq, FanoutExchange processingDlx) {
        return BindingBuilder.bind(processingDlq).to(processingDlx);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory cf, RabbitTemplate rabbitTemplate,
                                                                                 Jackson2JsonMessageConverter converter) {
        SimpleRabbitListenerContainerFactory fac = new SimpleRabbitListenerContainerFactory();
        fac.setConnectionFactory(cf);

        // Retry template: 3 attempts
        RetryTemplate retryTemplate = new RetryTemplate();
        FixedBackOffPolicy backOff = new FixedBackOffPolicy();
        backOff.setBackOffPeriod(1000);
        retryTemplate.setBackOffPolicy(backOff);
        SimpleRetryPolicy policy = new SimpleRetryPolicy(3);
        retryTemplate.setRetryPolicy(policy);

        // Build a retry interceptor that republishes to DLX after retries exhausted
        MethodInvocationRecoverer<Object> recoverer = new MethodInvocationRecoverer<>() {
            @Override
            public Object recover(Object[] args, Throwable cause) {
                try {
                    // If listener failure wrapped the original AMQP Message, try to extract it reflectively
                    try {
                        if (cause != null) {
                            Method m = cause.getClass().getMethod("getFailedMessage");
                            Object failedMsg = m.invoke(cause);
                            if (failedMsg instanceof Message) {
                                rabbitTemplate.send(PROCESSING_DLX, "", (Message) failedMsg);
                                System.out.println("[ProcessorService] Published failed Message to DLX");
                                return null;
                            }
                        }
                    } catch (NoSuchMethodException ignored) {
                        // not the wrapped listener exception, continue
                    }

                    Object payload = (args != null && args.length > 0) ? args[0] : null;
                    if (payload instanceof Message) {
                        rabbitTemplate.send(PROCESSING_DLX, "", (Message) payload);
                        System.out.println("[ProcessorService] Published raw Message to DLX");
                        return null;
                    }

                    if (payload != null) {
                        MessageProperties props = new MessageProperties();
                        props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
                        Message msg = converter.toMessage(payload, props);
                        rabbitTemplate.send(PROCESSING_DLX, "", msg);
                        System.out.println("[ProcessorService] Published to DLX: " + payload);
                        return null;
                    }

                    // fallback: publish a small diagnostic message
                    MessageProperties diagProps = new MessageProperties();
                    diagProps.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
                    String body = "Could not recover original payload; cause=" + cause;
                    rabbitTemplate.send(PROCESSING_DLX, "", new Message(body.getBytes(), diagProps));
                    System.out.println("[ProcessorService] Published diagnostic DLX message");
                } catch (Exception ex) {
                    System.err.println("[ProcessorService] Failed to publish to DLX: " + ex.getMessage());
                    ex.printStackTrace(System.err);
                }
                return null;
            }
        };

        MethodInterceptor interceptor = RetryInterceptorBuilder.stateless()
                .retryOperations(retryTemplate)
                .recoverer(recoverer)
                .build();

        // Set the advice chain on the container factory so retries and recoverer are applied
        fac.setAdviceChain(new Advice[]{(Advice) interceptor});

        return fac;
    }
}
