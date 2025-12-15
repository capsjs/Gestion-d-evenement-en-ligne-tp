package com.eventplatform.notificationservice.dto.incoming;

import java.time.LocalDateTime;

public class EventCancelledIncoming {

    private Long eventId;
    private String titre;
    private String raisonAnnulation;
    private Long organisateurId;
    private LocalDateTime timestamp;

    public EventCancelledIncoming() {}

    public Long getEventId() {
        return eventId;
    }

    public String getRaisonAnnulation() {
        return raisonAnnulation;
    }

    public Long getOrganisateurId() {
        return organisateurId;
    }
}
