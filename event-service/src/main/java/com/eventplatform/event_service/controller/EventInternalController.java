package com.eventplatform.event_service.controller;

import com.eventplatform.event_service.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur pour les opérations internes entre services.
 * Ces endpoints sont appelés par d'autres microservices (ex: billetterie).
 */
@RestController
@RequestMapping("/api/events/internal")
@RequiredArgsConstructor
public class EventInternalController {

    private final EventService eventService;

    /**
     * Réduit le nombre de places disponibles
     * Appelé par le service billetterie lors d'une réservation
     * POST /api/events/internal/{eventId}/decrease-seats
     */
    @PostMapping("/{eventId}/decrease-seats")
    public ResponseEntity<Void> decreaseAvailableSeats(
            @PathVariable Long eventId,
            @RequestParam int quantity
    ) {
        eventService.decreaseAvailableSeats(eventId, quantity);
        return ResponseEntity.ok().build();
    }

    /**
     * Augmente le nombre de places disponibles
     * Appelé par le service billetterie lors d'une annulation
     * POST /api/events/internal/{eventId}/increase-seats
     */
    @PostMapping("/{eventId}/increase-seats")
    public ResponseEntity<Void> increaseAvailableSeats(
            @PathVariable Long eventId,
            @RequestParam int quantity
    ) {
        eventService.increaseAvailableSeats(eventId, quantity);
        return ResponseEntity.ok().build();
    }
}
