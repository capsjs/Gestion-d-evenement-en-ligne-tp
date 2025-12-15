package com.eventplatform.event_service.model;

/**
 * États possibles d'un événement selon le diagramme d'états.
 * Cycle de vie: BROUILLON -> PUBLIE -> EN_COURS -> TERMINE
 * Avec possibilité d'ANNULE à tout moment avant TERMINE.
 */
public enum EventStatus {
    BROUILLON,      // Événement en cours de création
    PUBLIE,         // Événement publié et visible
    EN_COURS,       // Événement en cours de déroulement
    TERMINE,        // Événement terminé
    ANNULE          // Événement annulé
}
