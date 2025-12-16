package com.eventplatform.billetterie_service.dto;

import java.util.UUID;

public class EventCancelledEvent {
         private UUID eventId;

    public EventCancelledEvent() {}

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }
}
