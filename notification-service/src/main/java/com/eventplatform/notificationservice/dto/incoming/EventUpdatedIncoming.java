package com.eventplatform.notificationservice.dto.incoming;

import java.time.LocalDateTime;

public class EventUpdatedIncoming {

    private Long eventId;
    private String titre;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String lieu;
    private String changementsDescription;
    private LocalDateTime timestamp;

    public EventUpdatedIncoming() {}

    public Long getEventId() {
        return eventId;
    }

    public String getChangementsDescription() {
        return changementsDescription;
    }
}
