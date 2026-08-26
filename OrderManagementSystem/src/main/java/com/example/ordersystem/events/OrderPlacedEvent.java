package com.example.ordersystem.events;

public class OrderPlacedEvent {

    private String eventId;
    private String orderId;
    private String customerName;
    private double amount;

    public OrderPlacedEvent() {
    }

    public OrderPlacedEvent(String eventId, String orderId, String customerName, double amount) {
        this.eventId = eventId;
        this.orderId = orderId;
        this.customerName = customerName;
        this.amount = amount;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}