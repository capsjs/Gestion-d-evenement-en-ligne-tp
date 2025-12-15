package com.eventplatform.billetterie_service.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String TICKET_EXCHANGE = "ticket.exchange";
    public static final String TICKET_BOOKED_QUEUE = "ticket.booked.queue";
    public static final String TICKET_CANCELLED_QUEUE = "ticket.cancelled.queue";

    @Bean
    public TopicExchange ticketExchange() {
        return new TopicExchange(TICKET_EXCHANGE);
    }

    @Bean
    public Queue ticketBookedQueue() {
        return new Queue(TICKET_BOOKED_QUEUE);
    }

    @Bean
    public Queue ticketCancelledQueue() {
        return new Queue(TICKET_CANCELLED_QUEUE);
    }

    @Bean
    public Binding bookedBinding() {
        return BindingBuilder
                .bind(ticketBookedQueue())
                .to(ticketExchange())
                .with("ticket.booked");
    }

    @Bean
    public Binding cancelledBinding() {
        return BindingBuilder
                .bind(ticketCancelledQueue())
                .to(ticketExchange())
                .with("ticket.cancelled");
    }
}
