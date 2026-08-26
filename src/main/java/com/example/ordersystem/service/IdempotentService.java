package com.example.ordersystem.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IdempotentService {

    private final Set<String> processedEvents = new HashSet<>();

    public boolean isProcessed(String eventId) {

        if (processedEvents.contains(eventId)) {
            return true;
        }

        processedEvents.add(eventId);
        return false;
    }

}