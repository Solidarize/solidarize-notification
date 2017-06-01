package com.solidarize.notification.service;


import com.solidarize.notification.client.SolidarizeClient;
import com.solidarize.notification.client.SolidarizeEvent;
import com.solidarize.notification.repository.SolidarizeEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolidarizeEventsService {

    private SolidarizeClient client;
    private SolidarizeEventRepository repository;

    @Autowired
    public SolidarizeEventsService(SolidarizeClient client, SolidarizeEventRepository repository) {
        this.client = client;
        this.repository = repository;
    }

    public void saveEvents() {
        List<SolidarizeEvent> solidarizeEvents = client.fetchEvents();
        repository.save(new SolidarizeEventsWrapper(solidarizeEvents));
    }

    public List<SolidarizeEvent> getLatestEvents() {
        return repository.findAllByOrderByTimestamp().get(0).getSolidarizeEvents();
    }
}
