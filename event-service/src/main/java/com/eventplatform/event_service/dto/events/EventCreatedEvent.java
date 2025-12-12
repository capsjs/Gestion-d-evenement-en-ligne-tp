package com.eventplatform.event_service.dto.events;

import com.eventplatform.event_service.model.EventCategory;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Événement publié lors de la création d'un nouvel événement.
 * Déclenche les notifications vers les utilisateurs intéressés.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventCreatedEvent implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long eventId;
    private String titre;
    private String description;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String lieu;
    private EventCategory categorie;
    private Long organisateurId;
    private LocalDateTime timestamp;
}
