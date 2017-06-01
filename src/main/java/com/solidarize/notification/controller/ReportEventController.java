package com.solidarize.notification.controller;

import com.solidarize.notification.service.SolidarizeEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ReportEventController {

    private SolidarizeEventsService solidarizeEventsService;

    @Autowired
    public ReportEventController(SolidarizeEventsService solidarizeEventsService) {
        this.solidarizeEventsService = solidarizeEventsService;
    }

    @RequestMapping(value = "/report", method = POST)
    public void createReport() {
        solidarizeEventsService.saveEvents();
    }
}
