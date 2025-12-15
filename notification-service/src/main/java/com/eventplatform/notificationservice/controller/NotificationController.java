package com.eventplatform.notificationservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@RestController
public class NotificationController {

    /**
     * Route racine (route de sécurité)
     * Redirige vers /notification
     */
    @GetMapping("/")
    public RedirectView redirectToNotifications() {
        return new RedirectView("/notification");
    }

    /**
     * Liste des événements gérés par le service de notifications
     */
    @GetMapping("/notification")
    public Map<String, Object> supportedEvents() {
        return Map.of(
                "service", "notification-service",
                "description", "Service de notifications basé sur des événements",
                "listenedEvents", List.of(
                        "event.created",
                        "event.updated",
                        "event.cancelled",
                        "payment.processed",
                        "payment.failed"
                )
        );
    }
}
