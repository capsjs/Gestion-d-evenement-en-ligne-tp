package com.eventplatform.billetterie_service.model;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class EventSeat {

    @Id
    private UUID eventId;

    private int totalSeats;
    private int remainingSeats;

    public EventSeat() {
    }
    public EventSeat(UUID eventId, int totalSeats, int remainingSeats) {
        this.eventId = eventId;
        this.totalSeats = totalSeats;
        this.remainingSeats = remainingSeats;
    }

    public UUID getEventId() {
        return eventId;
    }
    public int getTotalSeats() {
        return totalSeats;
    }

    public int getRemainingSeats() {
        return remainingSeats;
    }

    public void setRemainingSeats(int seat) {
        this.remainingSeats = seat;
    }

}
