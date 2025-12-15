package com.eventplatform.billetterie_service.dto;

import com.eventplatform.billetterie_service.model.TypeTicket;

import java.util.UUID;

public class BookTicketRequest {

    private UUID eventId;
    private UUID userId;
    private TypeTicket type;
    private Float prix;

    public UUID getEventId() { return eventId; }
    public UUID getUserId() { return userId; }
    public TypeTicket getType() { return type; }
    public Float getPrix() { return prix; }
}
