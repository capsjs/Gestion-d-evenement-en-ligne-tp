package com.eventplatform.notificationservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    /**
     * Liste des événements gérés par le service de notifications
     */
    @GetMapping
    public Map<String, Object> supportedEvents() {
        return Map.of(
                "service", "notification-service",
                "description", "Service de notifications basé sur des événements",
                "listenedEvents", List.of(
                        "event.created",
                        "event.updated",
                        "event.cancelled",
                        "payment.processed",
                        "payment.failed"));
    }
}
