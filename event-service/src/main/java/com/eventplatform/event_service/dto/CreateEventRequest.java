package com.eventplatform.event_service.dto;

import com.eventplatform.event_service.model.EventCategory;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO pour la création d'un nouvel événement.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEventRequest {

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 3, max = 200, message = "Le titre doit contenir entre 3 et 200 caractères")
    private String titre;

    @Size(max = 2000, message = "La description ne peut pas dépasser 2000 caractères")
    private String description;

    @NotNull(message = "La date de début est obligatoire")
    @Future(message = "La date de début doit être dans le futur")
    private LocalDateTime dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDateTime dateFin;

    @NotBlank(message = "Le lieu est obligatoire")
    private String lieu;

    @NotNull(message = "La catégorie est obligatoire")
    private EventCategory categorie;

    @NotNull(message = "La capacité maximale est obligatoire")
    @Min(value = 1, message = "La capacité doit être au moins de 1")
    @Max(value = 100000, message = "La capacité ne peut pas dépasser 100000")
    private Integer capaciteMax;

    @NotNull(message = "L'ID de l'organisateur est obligatoire")
    private Long organisateurId;

    private String imageUrl;
}
