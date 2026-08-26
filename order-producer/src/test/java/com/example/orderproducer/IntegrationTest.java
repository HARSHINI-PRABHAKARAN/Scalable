package com.example.orderproducer;

import com.example.common.model.Address;
import com.example.common.model.Order;
import com.example.common.model.OrderItem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
public class IntegrationTest {

    @Container
    public static RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.9-management");

    static CachingConnectionFactory cf;
    static RabbitTemplate rt;
    static RabbitAdmin admin;

    @BeforeAll
    public static void setup() {
        rabbit.start();
        cf = new CachingConnectionFactory(rabbit.getHost(), rabbit.getAmqpPort());
        rt = new RabbitTemplate(cf);
        rt.setMessageConverter(new Jackson2JsonMessageConverter());
        admin = new RabbitAdmin(cf);

        TopicExchange ex = new TopicExchange("orders.exchange", true, false);
        Queue q1 = new Queue("order.payment.queue", true);
        Queue q2 = new Queue("order.notification.queue", true);
        Queue q3 = new Queue("order.analytics.queue", true);
        Queue q4 = new Queue("order.processing.queue", true);

        admin.declareExchange(ex);
        admin.declareQueue(q1);
        admin.declareQueue(q2);
        admin.declareQueue(q3);
        admin.declareQueue(q4);

        admin.declareBinding(BindingBuilder.bind(q1).to(ex).with("order.created"));
        admin.declareBinding(BindingBuilder.bind(q2).to(ex).with("order.created"));
        admin.declareBinding(BindingBuilder.bind(q3).to(ex).with("order.created"));
        admin.declareBinding(BindingBuilder.bind(q4).to(ex).with("order.created"));
    }

    @AfterAll
    public static void teardown() {
        try { cf.destroy(); } catch (Exception ignored) {}
        try { rabbit.stop(); } catch (Exception ignored) {}
    }

    @Test
    public void publishAndConsume_shouldDeliverToAllSubscribers() throws Exception {
        // set up simple consumers using basic receive polling
        BlockingQueue<Order> payments = new ArrayBlockingQueue<>(10);
        BlockingQueue<Order> notifications = new ArrayBlockingQueue<>(10);
        BlockingQueue<Order> analytics = new ArrayBlockingQueue<>(10);

        // send order
        Order order = new Order("test-1","cust-1", List.of(new OrderItem("p1",2)), new Address("s","c","000"));
        rt.convertAndSend("orders.exchange","order.created", order);

        // poll queues via basic receive
        // allow small delay for routing
        Thread.sleep(500);

        Object pm = rt.receiveAndConvert("order.payment.queue");
        Object nm = rt.receiveAndConvert("order.notification.queue");
        Object am = rt.receiveAndConvert("order.analytics.queue");

        assertNotNull(pm, "Payment queue should receive a message");
        assertNotNull(nm, "Notification queue should receive a message");
        assertNotNull(am, "Analytics queue should receive a message");

        // basic type checks
        assertEquals(Order.class, pm.getClass());
        assertEquals(Order.class, nm.getClass());
        assertEquals(Order.class, am.getClass());
    }
}
