package com.eventplatform.event_service.dto.events;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Événement publié lors de la modification d'un événement.
 * Déclenche les notifications vers les participants concernés et met à jour la billetterie.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventUpdatedEvent implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long eventId;
    private String titre;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String lieu;
    private String changementsDescription;
    private LocalDateTime timestamp;
}
