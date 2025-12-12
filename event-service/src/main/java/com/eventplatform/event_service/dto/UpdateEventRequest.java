package com.eventplatform.event_service.dto;

import com.eventplatform.event_service.model.EventCategory;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO pour la mise à jour d'un événement existant.
 * Tous les champs sont optionnels pour permettre des mises à jour partielles.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEventRequest {

    @Size(min = 3, max = 200, message = "Le titre doit contenir entre 3 et 200 caractères")
    private String titre;

    @Size(max = 2000, message = "La description ne peut pas dépasser 2000 caractères")
    private String description;

    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    private String lieu;

    private EventCategory categorie;

    private Integer capaciteMax;

    private String imageUrl;
}
