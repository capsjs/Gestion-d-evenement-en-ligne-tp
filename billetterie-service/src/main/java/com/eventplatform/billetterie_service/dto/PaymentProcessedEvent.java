package com.eventplatform.billetterie_service.dto;

import java.util.UUID;

public class PaymentProcessedEvent {

    private UUID ticketId;

    public PaymentProcessedEvent() {
    }

    public PaymentProcessedEvent(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }
}
