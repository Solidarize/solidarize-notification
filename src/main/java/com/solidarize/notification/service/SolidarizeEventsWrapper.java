package com.solidarize.notification.service;


import com.solidarize.notification.client.SolidarizeEvent;

import java.time.LocalDateTime;
import java.util.List;

public class SolidarizeEventsWrapper {

    private List<SolidarizeEvent> solidarizeEvents;
    private LocalDateTime timestamp;

    public SolidarizeEventsWrapper(List<SolidarizeEvent> solidarizeEvents) {
        this.solidarizeEvents = solidarizeEvents;
        this.timestamp = LocalDateTime.now();
    }

    public List<SolidarizeEvent> getSolidarizeEvents() {
        return solidarizeEvents;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
