package com.eventplatform.event_service.controller;

import com.eventplatform.event_service.dto.*;
import com.eventplatform.event_service.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des événements.
 * Expose les APIs pour CRUD et opérations métier sur les événements.
 */
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventController {

    private final EventService eventService;

    /**
     * Crée un nouvel événement
     * POST /api/events
     */
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {
        EventResponse response = eventService.createEvent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Récupère un événement par son ID
     * GET /api/events/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        EventResponse response = eventService.getEventById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Récupère tous les événements
     * GET /api/events
     */
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<EventResponse> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    /**
     * Récupère les événements d'un organisateur
     * GET /api/events/organizer/{organizerId}
     */
    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<EventResponse>> getEventsByOrganizer(@PathVariable Long organizerId) {
        List<EventResponse> events = eventService.getEventsByOrganizer(organizerId);
        return ResponseEntity.ok(events);
    }

    /**
     * Recherche d'événements avec filtres
     * GET /api/events/search
     */
    @GetMapping("/search")
    public ResponseEntity<List<EventResponse>> searchEvents(
            @RequestParam(required = false) String titre,
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String lieu,
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin,
            @RequestParam(required = false) Long organisateurId) {
        EventSearchCriteria criteria = EventSearchCriteria.builder()
                .titre(titre)
                .categorie(categorie != null ? com.eventplatform.event_service.model.EventCategory.valueOf(categorie)
                        : null)
                .statut(statut != null ? com.eventplatform.event_service.model.EventStatus.valueOf(statut) : null)
                .lieu(lieu)
                .dateDebut(dateDebut != null ? java.time.LocalDateTime.parse(dateDebut) : null)
                .dateFin(dateFin != null ? java.time.LocalDateTime.parse(dateFin) : null)
                .organisateurId(organisateurId)
                .build();

        List<EventResponse> events = eventService.searchEvents(criteria);
        return ResponseEntity.ok(events);
    }

    /**
     * Récupère les événements à venir
     * GET /api/events/upcoming
     */
    @GetMapping("/upcoming")
    public ResponseEntity<List<EventResponse>> getUpcomingEvents() {
        List<EventResponse> events = eventService.getUpcomingEvents();
        return ResponseEntity.ok(events);
    }

    /**
     * Récupère les événements en cours
     * GET /api/events/ongoing
     */
    @GetMapping("/ongoing")
    public ResponseEntity<List<EventResponse>> getOngoingEvents() {
        List<EventResponse> events = eventService.getOngoingEvents();
        return ResponseEntity.ok(events);
    }

    /**
     * Met à jour un événement
     * PUT /api/events/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEventRequest request) {
        EventResponse response = eventService.updateEvent(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Publie un événement (brouillon -> publié)
     * POST /api/events/{id}/publish
     */
    @PostMapping("/{id}/publish")
    public ResponseEntity<EventResponse> publishEvent(@PathVariable Long id) {
        EventResponse response = eventService.publishEvent(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Annule un événement
     * POST /api/events/{id}/cancel
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<EventResponse> cancelEvent(
            @PathVariable Long id,
            @RequestBody(required = false) CancelEventRequest request) {
        String reason = request != null ? request.getReason() : "Aucune raison fournie";
        EventResponse response = eventService.cancelEvent(id, reason);
        return ResponseEntity.ok(response);
    }

    /**
     * Démarre un événement
     * POST /api/events/{id}/start
     */
    @PostMapping("/{id}/start")
    public ResponseEntity<EventResponse> startEvent(@PathVariable Long id) {
        EventResponse response = eventService.startEvent(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Termine un événement
     * POST /api/events/{id}/complete
     */
    @PostMapping("/{id}/complete")
    public ResponseEntity<EventResponse> completeEvent(@PathVariable Long id) {
        EventResponse response = eventService.completeEvent(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Supprime un événement (seulement en brouillon)
     * DELETE /api/events/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * DTO pour la requête d'annulation
     */
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class CancelEventRequest {
        private String reason;
    }
}
