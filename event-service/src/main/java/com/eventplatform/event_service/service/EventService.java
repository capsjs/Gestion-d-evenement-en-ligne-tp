package com.eventplatform.event_service.service;

import com.eventplatform.event_service.dto.*;
import com.eventplatform.event_service.exception.*;
import com.eventplatform.event_service.model.Event;
import com.eventplatform.event_service.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public EventResponse createEvent(CreateEventRequest request) {
    log.info("Création d'un nouvel événement: {}", request.getTitre());

    // Version simple sans validation complexe au début
    Event event = eventMapper.toEntity(request);
    event = eventRepository.save(event);

    log.info("Événement créé avec ID: {}", event.getId());
    return eventMapper.toResponse(event);
  }

  @Transactional(readOnly = true)
  public EventResponse getEventById(Long id) {
    log.debug("Récupération de l'événement ID: {}", id);
    Event event = eventRepository.findById(id)
        .orElseThrow(() -> new EventNotFoundException(id));
    return eventMapper.toResponse(event);
  }

  @Transactional(readOnly = true)
  public List<EventResponse> getAllEvents() {
    log.debug("Récupération de tous les événements");
    return eventRepository.findAll().stream()
        .map(eventMapper::toResponse)
        .collect(Collectors.toList());
  }
}