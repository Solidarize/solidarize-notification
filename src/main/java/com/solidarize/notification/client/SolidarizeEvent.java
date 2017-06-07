package com.solidarize.notification.client;


public class SolidarizeEvent {
    private String title;
    private String event_time;
    private String description;

    public SolidarizeEvent() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEvent_time() {
        return event_time;
    }

    public String getDescription() {
        return description;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
