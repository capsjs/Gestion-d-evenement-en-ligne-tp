package com.eventplatform.billetterie_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class EventUpdatedEvent {
        private UUID eventId;
        private String title;
        private LocalDateTime dateDebut;
        private LocalDateTime dateFin;

        public EventUpdatedEvent() {
        }

        public UUID getEventId() {
                return eventId;
        }

        public String getTitle() {
                return title;
        }

        public LocalDateTime getDateDebut() {
                return dateDebut;
        }
        public LocalDateTime getDateFin() {
                return dateFin;
        }
}
