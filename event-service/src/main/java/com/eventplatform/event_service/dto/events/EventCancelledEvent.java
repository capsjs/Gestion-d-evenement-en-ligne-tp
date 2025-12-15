package com.eventplatform.event_service.dto.events;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Événement publié lors de l'annulation d'un événement.
 * Déclenche l'annulation des billets, les remboursements et les notifications en cascade.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventCancelledEvent implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long eventId;
    private String titre;
    private String raisonAnnulation;
    private Long organisateurId;
    private LocalDateTime timestamp;
}
