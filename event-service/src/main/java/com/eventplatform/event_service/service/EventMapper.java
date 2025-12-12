package com.eventplatform.event_service.service;

import com.eventplatform.event_service.dto.CreateEventRequest;
import com.eventplatform.event_service.dto.EventResponse;
import com.eventplatform.event_service.model.Event;
import com.eventplatform.event_service.model.EventStatus;
import org.springframework.stereotype.Component;

/**
 * Mapper pour convertir entre les entités Event et les DTOs.
 */
@Component
public class EventMapper {

  /**
   * Convertit un CreateEventRequest en entité Event
   */
  public Event toEntity(CreateEventRequest request) {
    return Event.builder()
        .titre(request.getTitre())
        .description(request.getDescription())
        .dateDebut(request.getDateDebut())
        .dateFin(request.getDateFin())
        .lieu(request.getLieu())
        .categorie(request.getCategorie())
        .capaciteMax(request.getCapaciteMax())
        .placesDisponibles(request.getCapaciteMax())
        .organisateurId(request.getOrganisateurId())
        .imageUrl(request.getImageUrl())
        .statut(EventStatus.BROUILLON)
        .build();
  }

  /**
   * Convertit une entité Event en EventResponse
   */
  public EventResponse toResponse(Event event) {
    return EventResponse.builder()
        .id(event.getId())
        .titre(event.getTitre())
        .description(event.getDescription())
        .dateDebut(event.getDateDebut())
        .dateFin(event.getDateFin())
        .lieu(event.getLieu())
        .categorie(event.getCategorie())
        .statut(event.getStatut())
        .capaciteMax(event.getCapaciteMax())
        .placesDisponibles(event.getPlacesDisponibles())
        .organisateurId(event.getOrganisateurId())
        .imageUrl(event.getImageUrl())
        .dateCreation(event.getDateCreation())
        .dateModification(event.getDateModification())
        .isReservable(event.isReservable())
        .isModifiable(event.isModifiable())
        .isAnnulable(event.isAnnulable())
        .build();
  }
}
