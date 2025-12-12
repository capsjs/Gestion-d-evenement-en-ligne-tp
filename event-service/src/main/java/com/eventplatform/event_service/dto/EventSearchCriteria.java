package com.eventplatform.event_service.dto;

import com.eventplatform.event_service.model.EventCategory;
import com.eventplatform.event_service.model.EventStatus;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO pour les critères de recherche d'événements.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventSearchCriteria {

    private String titre;
    private EventCategory categorie;
    private EventStatus statut;
    private String lieu;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Long organisateurId;
}
