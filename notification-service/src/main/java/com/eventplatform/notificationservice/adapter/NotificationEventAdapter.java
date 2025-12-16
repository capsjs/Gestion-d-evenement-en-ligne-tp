package com.eventplatform.notificationservice.adapter;

import com.eventplatform.notificationservice.dto.incoming.*;
import com.eventplatform.notificationservice.domain.EventNotification;

public class NotificationEventAdapter {

    public static EventNotification from(EventCreatedIncoming e) {
        return new EventNotification(
                e.getOrganisateurId(),
                "Nouvel événement créé : " + e.getTitre()
        );
    }

    public static EventNotification from(EventUpdatedIncoming e) {
        return new EventNotification(
                e.getEventId(),
                "L'événement a été mis à jour : " + e.getChangementsDescription()
        );
    }

    public static EventNotification from(EventCancelledIncoming e) {
        return new EventNotification(
                e.getOrganisateurId(),
                "Événement annulé : " + e.getRaisonAnnulation()
        );
    }
}
