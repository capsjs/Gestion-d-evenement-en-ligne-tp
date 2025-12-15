package com.eventplatform.billetterie_service.dto;

import java.util.UUID;

public class TicketCancelledEvent {

    private UUID ticketId;
    private UUID eventId;
    private UUID userId;

    public TicketCancelledEvent(UUID ticketId, UUID eventId, UUID userId) {
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.userId = userId;
    }

    public UUID getTicketId() { return ticketId; }
    public UUID getEventId() { return eventId; }
    public UUID getUserId() { return userId; }
}