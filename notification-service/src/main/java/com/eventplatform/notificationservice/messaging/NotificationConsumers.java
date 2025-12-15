package com.eventplatform.notificationservice.messaging;

import com.eventplatform.notificationservice.adapter.NotificationEventAdapter;
import com.eventplatform.notificationservice.dto.incoming.*;
import com.eventplatform.notificationservice.notification.NotificationService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class NotificationConsumers {

    private final NotificationService service;

    public NotificationConsumers(NotificationService service) {
        this.service = service;
    }

    @Bean
    public Consumer<EventCreatedIncoming> onEventCreated() {
        return e -> service.send(NotificationEventAdapter.from(e));
    }

    @Bean
    public Consumer<EventUpdatedIncoming> onEventUpdated() {
        return e -> service.send(NotificationEventAdapter.from(e));
    }

    @Bean
    public Consumer<EventCancelledIncoming> onEventCancelled() {
        return e -> service.send(NotificationEventAdapter.from(e));
    }

    @Bean
    public Consumer<PaymentProcessedIncoming> onPaymentProcessed() {
        return service::onPaymentProcessed;
    }
}
