package com.example.ordersystem.events;

public class PaymentCompletedEvent {

    private String eventId;
    private String orderId;
    private String paymentStatus;

    public PaymentCompletedEvent() {
    }

    public PaymentCompletedEvent(String eventId, String orderId, String paymentStatus) {
        this.eventId = eventId;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}