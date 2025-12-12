package com.eventplatform.event_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entité représentant un événement dans le système.
 * Correspond au diagramme de classes et au diagramme d'états définis dans l'architecture.
 */
@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateDebut;

    @Column(nullable = false)
    private LocalDateTime dateFin;

    @Column(nullable = false)
    private String lieu;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventCategory categorie;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStatus statut;

    @Column(nullable = false)
    private Integer capaciteMax;

    @Column(nullable = false)
    private Integer placesDisponibles;

    @Column(nullable = false)
    private Long organisateurId; // Référence vers le User Service

    private String imageUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(nullable = false)
    private LocalDateTime dateModification;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
        if (statut == null) {
            statut = EventStatus.BROUILLON;
        }
        if (placesDisponibles == null && capaciteMax != null) {
            placesDisponibles = capaciteMax;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }

    /**
     * Vérifie si l'événement peut être modifié
     */
    public boolean isModifiable() {
        return statut == EventStatus.BROUILLON || statut == EventStatus.PUBLIE;
    }

    /**
     * Vérifie si l'événement peut être annulé
     */
    public boolean isAnnulable() {
        return statut != EventStatus.TERMINE && statut != EventStatus.ANNULE;
    }

    /**
     * Vérifie si des billets peuvent être réservés
     */
    public boolean isReservable() {
        return statut == EventStatus.PUBLIE && 
               placesDisponibles > 0 && 
               LocalDateTime.now().isBefore(dateDebut);
    }
}
