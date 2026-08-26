package com.example.ordersystem;

import com.example.ordersystem.boundedcontext.order.OrderContext;
import com.example.ordersystem.boundedcontext.payment.PaymentContext;
import com.example.ordersystem.boundedcontext.shipping.ShippingContext;

public class ContextDemo {

    public static void main(String[] args) {

        OrderContext orderContext = new OrderContext();
        PaymentContext paymentContext = new PaymentContext();
        ShippingContext shippingContext = new ShippingContext();

        orderContext.showContext();
        paymentContext.showContext();
        shippingContext.showContext();
    }
}