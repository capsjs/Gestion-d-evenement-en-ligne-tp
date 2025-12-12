package com.eventplatform.billetterie_service.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    public Ticket(UUID eventId, UUID userId, TypeTicket type, Float prix) {
        this.eventId = eventId;
        this.userId = userId;
        this.type = type;
        this.prix = prix;
        this.etat = EtatTicket.RESERVE;
        this.qrCode = UUID.randomUUID().toString();
    }

}