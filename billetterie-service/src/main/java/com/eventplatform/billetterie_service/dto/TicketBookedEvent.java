package com.eventplatform.billetterie_service.dto;

import java.util.UUID;


public class TicketBookedEvent {
    private UUID ticketId;
    private UUID eventId;
    private UUID userId;
    private Float prix;

    public TicketBookedEvent(UUID ticketId, UUID eventId, UUID userId, Float prix) {
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.userId = userId;
        this.prix = prix;
    }

    public UUID getTicketId() { return ticketId; }
    public UUID getEventId() { return eventId; }
    public UUID getUserId() { return userId; }
    public Float getPrix() { return prix; }
}
