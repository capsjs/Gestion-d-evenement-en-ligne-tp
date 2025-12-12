package com.eventplatform.event_service.dto;

import com.eventplatform.event_service.model.EventCategory;
import com.eventplatform.event_service.model.EventStatus;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO de réponse contenant les informations complètes d'un événement.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {

    private Long id;
    private String titre;
    private String description;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String lieu;
    private EventCategory categorie;
    private EventStatus statut;
    private Integer capaciteMax;
    private Integer placesDisponibles;
    private Long organisateurId;
    private String imageUrl;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private Boolean isReservable;
    private Boolean isModifiable;
    private Boolean isAnnulable;
}
