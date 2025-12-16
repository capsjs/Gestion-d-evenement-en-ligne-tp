package com.eventplatform.event_service.service;

import com.eventplatform.event_service.config.RabbitMQConfig;
import com.eventplatform.event_service.dto.events.EventCancelledEvent;
import com.eventplatform.event_service.dto.events.EventCreatedEvent;
import com.eventplatform.event_service.dto.events.EventUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Service responsable de la publication des événements métier vers RabbitMQ.
 * Implémente le pattern Event-Driven pour la communication inter-services.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Publie un événement de création
     */
    public void publishEventCreated(EventCreatedEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EVENT_EXCHANGE,
                RabbitMQConfig.EVENT_CREATED_ROUTING_KEY,
                event
            );
            log.info("EventCreatedEvent publié pour l'événement ID: {}", event.getEventId());
        } catch (Exception e) {
            log.error("Erreur lors de la publication de EventCreatedEvent: {}", e.getMessage(), e);
            throw new RuntimeException("Échec de la publication de l'événement", e);
        }
    }

    /**
     * Publie un événement de mise à jour
     */
    public void publishEventUpdated(EventUpdatedEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EVENT_EXCHANGE,
                RabbitMQConfig.EVENT_UPDATED_ROUTING_KEY,
                event
            );
            log.info("EventUpdatedEvent publié pour l'événement ID: {}", event.getEventId());
        } catch (Exception e) {
            log.error("Erreur lors de la publication de EventUpdatedEvent: {}", e.getMessage(), e);
            throw new RuntimeException("Échec de la publication de l'événement", e);
        }
    }

    /**
     * Publie un événement d'annulation
     */
    public void publishEventCancelled(EventCancelledEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EVENT_EXCHANGE,
                RabbitMQConfig.EVENT_CANCELLED_ROUTING_KEY,
                event
            );
            log.info("EventCancelledEvent publié pour l'événement ID: {}", event.getEventId());
        } catch (Exception e) {
            log.error("Erreur lors de la publication de EventCancelledEvent: {}", e.getMessage(), e);
            throw new RuntimeException("Échec de la publication de l'événement", e);
        }
    }
}
