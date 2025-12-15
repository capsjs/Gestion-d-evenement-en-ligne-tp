package com.eventplatform.notificationservice.dto.incoming;

import java.time.LocalDateTime;

public class EventCreatedIncoming {

    private Long eventId;
    private String titre;
    private String description;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String lieu;
    private String categorie;
    private Long organisateurId;
    private LocalDateTime timestamp;

    public EventCreatedIncoming() {}

    public Long getEventId() {
        return eventId;
    }

    public String getTitre() {
        return titre;
    }

    public Long getOrganisateurId() {
        return organisateurId;
    }
}
