package com.eventplatform.billetterie_service.dto;

import java.util.UUID;

public class PaymentFailedEvent {

    private UUID ticketId;
    private String reason;

    public PaymentFailedEvent() {
    }

    public PaymentFailedEvent(UUID ticketId, String reason) {
        this.ticketId = ticketId;
        this.reason = reason;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
