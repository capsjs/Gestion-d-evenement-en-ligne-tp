package com.eventplatform.billetterie_service.messaging.listener;

import com.eventplatform.billetterie_service.dto.PaymentFailedEvent;
import com.eventplatform.billetterie_service.dto.PaymentProcessedEvent;
import com.eventplatform.billetterie_service.service.BilletterieService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {

    private final BilletterieService billetterieService;

    public PaymentEventListener(BilletterieService billetterieService) {
        this.billetterieService = billetterieService;
    }

    @RabbitListener(queues = "billetterie.queue")
    public void onPaymentProcessed(PaymentProcessedEvent event) {
        billetterieService.confirmTicket(event.getTicketId());
    }

    @RabbitListener(queues = "billetterie.queue")
    public void onPaymentFailed(PaymentFailedEvent event) {
        billetterieService.cancelTicket(event.getTicketId());
    }

}
