package com.solidarize.notification.service;


import com.solidarize.notification.client.SolidarizeEvent;

import java.time.LocalDate;
import java.util.List;

public class SolidarizeEventsWrapper {

    private List<SolidarizeEvent> solidarizeEvents;
    private LocalDate timestamp;

    public SolidarizeEventsWrapper(List<SolidarizeEvent> solidarizeEvents) {
        this.solidarizeEvents = solidarizeEvents;
        this.timestamp = LocalDate.now();
    }

    public List<SolidarizeEvent> getSolidarizeEvents() {
        return solidarizeEvents;
    }

    public LocalDate getLocalDate() {
        return timestamp;
    }
}
