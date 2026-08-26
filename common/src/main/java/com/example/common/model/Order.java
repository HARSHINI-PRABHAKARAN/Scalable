package com.example.common.model;

import java.util.List;

public class Order {
    private String id;
    private String customerId;
    private List<OrderItem> items;
    private Address address;

    public Order() {
    }

    public Order(String id, String customerId, List<OrderItem> items, Address address) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
