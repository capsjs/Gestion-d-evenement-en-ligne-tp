package com.eventplatform.billetterie_service.messaging.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eventplatform.billetterie_service.dto.EventCancelledEvent;
import com.eventplatform.billetterie_service.dto.EventUpdatedEvent;
import com.eventplatform.billetterie_service.service.BilletterieService;

@Component
public class EventEventListener {

    private final BilletterieService billetterieService;

    public EventEventListener(BilletterieService billetterieService) {
        this.billetterieService = billetterieService;
    }

    @RabbitListener(queues = "event.updated.queue")
    public void onEventUpdated(EventUpdatedEvent event) {
        billetterieService.handleEventUpdate(event);
    }

    @RabbitListener(queues = "event.cancelled.queue")
    public void onEventCancelled(EventCancelledEvent event) {
        billetterieService.cancelTicketsForEvent(event.getEventId());
    }
}
