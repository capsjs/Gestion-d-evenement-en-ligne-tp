package com.eventplatform.billetterie_service.model;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID eventId;
    private UUID userId;

    @Enumerated(EnumType.STRING)
    private TypeTicket type;

    private Float prix;
    private String qrCode;

    @Enumerated(EnumType.STRING)
    private EtatTicket etat;

    public Ticket() {
    }

    public Ticket(UUID eventId, UUID userId, TypeTicket type, Float prix) {
        this.eventId = eventId;
        this.userId = userId;
        this.type = type;
        this.prix = prix;
        this.etat = EtatTicket.RESERVE;
        this.qrCode = UUID.randomUUID().toString();
    }

    public UUID getId() {
        return id;
    }

    public UUID getEventId() {
        return eventId;
    }
    public TypeTicket getType() {
        return type;
    }
    public UUID getUserId() {
        return userId;
    }
    public Float getPrix() {
        return prix;
    }
    public String getqrCode() {
        return qrCode;
    }

    public EtatTicket getEtat() {
        return etat;
    }

    public void setEtat(EtatTicket etat) {
        this.etat = etat;
    }
}