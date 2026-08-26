package com.example.ordersystem.events;

public class ShippingAssignedEvent {

    private String eventId;
    private String orderId;
    private String shippingStatus;

    public ShippingAssignedEvent() {
    }

    public ShippingAssignedEvent(String eventId, String orderId, String shippingStatus) {
        this.eventId = eventId;
        this.orderId = orderId;
        this.shippingStatus = shippingStatus;
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

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }
}