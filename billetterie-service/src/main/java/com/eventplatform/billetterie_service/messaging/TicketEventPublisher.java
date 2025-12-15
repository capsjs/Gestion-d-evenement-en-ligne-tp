package com.eventplatform.billetterie_service.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.eventplatform.billetterie_service.dto.TicketBookedEvent;
import com.eventplatform.billetterie_service.dto.TicketCancelledEvent;

@Component
public class TicketEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public TicketEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishTicketBooked(TicketBookedEvent event) {
        rabbitTemplate.convertAndSend(
                "ticket.exchange",
                "ticket.booked",
                event
        );
    }

    public void publishTicketCancelled(TicketCancelledEvent event) {
        rabbitTemplate.convertAndSend(
                "ticket.exchange",
                "ticket.cancelled",
                event
        );
    }
}
