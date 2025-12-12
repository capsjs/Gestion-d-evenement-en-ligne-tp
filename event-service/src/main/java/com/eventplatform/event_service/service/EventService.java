package com.eventplatform.event_service.service;

import com.eventplatform.event_service.dto.*;
import com.eventplatform.event_service.exception.*;
import com.eventplatform.event_service.model.Event;
import com.eventplatform.event_service.model.EventStatus;
import com.eventplatform.event_service.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service métier pour la gestion des événements.
 * Implémente toute la logique métier et orchestre les appels au repository et à
 * l'event publisher.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EventService {

  private final EventRepository eventRepository;
  private final EventMapper eventMapper;

  /**
   * Crée un nouvel événement en mode brouillon
   */
  public EventResponse createEvent(CreateEventRequest request) {
    log.info("Création d'un nouvel événement: {}", request.getTitre());

    // Validation des dates
    validateEventDates(request.getDateDebut(), request.getDateFin());

    // Création de l'entité
    Event event = eventMapper.toEntity(request);
    event = eventRepository.save(event);

    log.info("Événement créé avec succès, ID: {}", event.getId());

    return eventMapper.toResponse(event);
  }

  /**
   * Récupère un événement par son ID
   */
  @Transactional(readOnly = true)
  public EventResponse getEventById(Long id) {
    log.debug("Récupération de l'événement ID: {}", id);
    Event event = findEventById(id);
    return eventMapper.toResponse(event);
  }

  /**
   * Récupère tous les événements
   */
  @Transactional(readOnly = true)
  public List<EventResponse> getAllEvents() {
    log.debug("Récupération de tous les événements");
    return eventRepository.findAll().stream()
        .map(eventMapper::toResponse)
        .collect(Collectors.toList());
  }

  /**
   * Récupère les événements d'un organisateur
   */
  @Transactional(readOnly = true)
  public List<EventResponse> getEventsByOrganizer(Long organizerId) {
    log.debug("Récupération des événements de l'organisateur ID: {}", organizerId);
    return eventRepository.findByOrganisateurId(organizerId).stream()
        .map(eventMapper::toResponse)
        .collect(Collectors.toList());
  }

  /**
   * Recherche d'événements selon des critères
   */
  @Transactional(readOnly = true)
  public List<EventResponse> searchEvents(EventSearchCriteria criteria) {
    log.debug("Recherche d'événements avec critères: {}", criteria);

    if (criteria.getOrganisateurId() != null) {
      return getEventsByOrganizer(criteria.getOrganisateurId());
    }

    return eventRepository.searchEvents(
        criteria.getCategorie(),
        criteria.getStatut(),
        criteria.getDateDebut(),
        criteria.getDateFin(),
        criteria.getLieu(),
        criteria.getTitre()).stream()
        .map(eventMapper::toResponse)
        .collect(Collectors.toList());
  }

  /**
   * Récupère les événements à venir
   */
  @Transactional(readOnly = true)
  public List<EventResponse> getUpcomingEvents() {
    log.debug("Récupération des événements à venir");
    return eventRepository.findUpcomingEvents(LocalDateTime.now()).stream()
        .map(eventMapper::toResponse)
        .collect(Collectors.toList());
  }

  /**
   * Récupère les événements en cours
   */
  @Transactional(readOnly = true)
  public List<EventResponse> getOngoingEvents() {
    log.debug("Récupération des événements en cours");
    return eventRepository.findOngoingEvents(LocalDateTime.now()).stream()
        .map(eventMapper::toResponse)
        .collect(Collectors.toList());
  }

  /**
   * Met à jour un événement
   */
  public EventResponse updateEvent(Long id, UpdateEventRequest request) {
    log.info("Mise à jour de l'événement ID: {}", id);

    Event event = findEventById(id);

    // Vérification que l'événement est modifiable
    if (!event.isModifiable()) {
      throw new InvalidEventOperationException(
          "L'événement ne peut pas être modifié dans l'état: " + event.getStatut());
    }

    // Validation des nouvelles dates si elles sont fournies
    LocalDateTime newDateDebut = request.getDateDebut() != null ? request.getDateDebut() : event.getDateDebut();
    LocalDateTime newDateFin = request.getDateFin() != null ? request.getDateFin() : event.getDateFin();
    validateEventDates(newDateDebut, newDateFin);

    // Mise à jour de l'entité
    eventMapper.updateEntity(event, request);
    event = eventRepository.save(event);

    log.info("Événement mis à jour avec succès, ID: {}", event.getId());

    return eventMapper.toResponse(event);
  }

  /**
   * Publie un événement (passe de BROUILLON à PUBLIE)
   */
  public EventResponse publishEvent(Long id) {
    log.info("Publication de l'événement ID: {}", id);

    Event event = findEventById(id);

    if (event.getStatut() != EventStatus.BROUILLON) {
      throw new InvalidEventOperationException(
          "Seuls les événements en brouillon peuvent être publiés");
    }

    event.setStatut(EventStatus.PUBLIE);
    event = eventRepository.save(event);

    log.info("Événement publié avec succès, ID: {}", event.getId());

    return eventMapper.toResponse(event);
  }

  /**
   * Annule un événement
   */
  public EventResponse cancelEvent(Long id, String reason) {
    log.info("Annulation de l'événement ID: {}", id);

    Event event = findEventById(id);

    if (!event.isAnnulable()) {
      throw new InvalidEventOperationException(
          "L'événement ne peut pas être annulé dans l'état: " + event.getStatut());
    }

    event.setStatut(EventStatus.ANNULE);
    event = eventRepository.save(event);

    log.info("Événement annulé avec succès, ID: {}", event.getId());

    return eventMapper.toResponse(event);
  }

  /**
   * Démarre un événement (passe à EN_COURS)
   */
  public EventResponse startEvent(Long id) {
    log.info("Démarrage de l'événement ID: {}", id);

    Event event = findEventById(id);

    if (event.getStatut() != EventStatus.PUBLIE) {
      throw new InvalidEventOperationException(
          "Seuls les événements publiés peuvent être démarrés");
    }

    event.setStatut(EventStatus.EN_COURS);
    event = eventRepository.save(event);

    log.info("Événement démarré avec succès, ID: {}", event.getId());

    return eventMapper.toResponse(event);
  }

  /**
   * Termine un événement
   */
  public EventResponse completeEvent(Long id) {
    log.info("Clôture de l'événement ID: {}", id);

    Event event = findEventById(id);

    if (event.getStatut() != EventStatus.EN_COURS && event.getStatut() != EventStatus.PUBLIE) {
      throw new InvalidEventOperationException(
          "Seuls les événements en cours ou publiés peuvent être clôturés");
    }

    event.setStatut(EventStatus.TERMINE);
    event = eventRepository.save(event);

    log.info("Événement clôturé avec succès, ID: {}", event.getId());

    return eventMapper.toResponse(event);
  }

  /**
   * Supprime un événement (seulement en brouillon)
   */
  public void deleteEvent(Long id) {
    log.info("Suppression de l'événement ID: {}", id);

    Event event = findEventById(id);

    if (event.getStatut() != EventStatus.BROUILLON) {
      throw new InvalidEventOperationException(
          "Seuls les événements en brouillon peuvent être supprimés. Utilisez l'annulation pour les autres.");
    }

    eventRepository.delete(event);
    log.info("Événement supprimé avec succès, ID: {}", id);
  }

  /**
   * Réduit le nombre de places disponibles (appelé par le service billetterie)
   */
  public void decreaseAvailableSeats(Long eventId, int quantity) {
    log.info("Réduction de {} places pour l'événement ID: {}", quantity, eventId);

    Event event = findEventById(eventId);

    if (event.getPlacesDisponibles() < quantity) {
      throw new InvalidEventOperationException(
          "Pas assez de places disponibles. Disponibles: " + event.getPlacesDisponibles());
    }

    event.setPlacesDisponibles(event.getPlacesDisponibles() - quantity);
    eventRepository.save(event);

    log.info("Places réduites avec succès. Nouvelles places disponibles: {}", event.getPlacesDisponibles());
  }

  /**
   * Augmente le nombre de places disponibles (appelé en cas d'annulation)
   */
  public void increaseAvailableSeats(Long eventId, int quantity) {
    log.info("Augmentation de {} places pour l'événement ID: {}", quantity, eventId);

    Event event = findEventById(eventId);

    int newAvailable = Math.min(event.getPlacesDisponibles() + quantity, event.getCapaciteMax());
    event.setPlacesDisponibles(newAvailable);
    eventRepository.save(event);

    log.info("Places augmentées avec succès. Nouvelles places disponibles: {}", event.getPlacesDisponibles());
  }

  // Méthodes privées utilitaires

  private Event findEventById(Long id) {
    return eventRepository.findById(id)
        .orElseThrow(() -> new EventNotFoundException(id));
  }

  private void validateEventDates(LocalDateTime dateDebut, LocalDateTime dateFin) {
    if (dateDebut.isAfter(dateFin)) {
      throw new InvalidEventDataException(
          "La date de début doit être antérieure à la date de fin");
    }

    if (dateDebut.isBefore(LocalDateTime.now().plusHours(1))) {
      throw new InvalidEventDataException(
          "La date de début doit être au moins 1 heure dans le futur");
    }
  }
}
