package com.eventplatform.billetterie_service.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EVENT_EXCHANGE = "event-platform.exchange";
    public static final String BILLETTERIE_QUEUE = "billetterie.queue";

    @Bean
    public TopicExchange eventExchange() {
        return new TopicExchange(EVENT_EXCHANGE);
    }

    @Bean
    public Queue billetterieQueue() {
        return QueueBuilder.durable(BILLETTERIE_QUEUE).build();
    }
    @Bean
    public Binding paymentProcessedBinding() {
        return BindingBuilder
                .bind(billetterieQueue())
                .to(eventExchange())
                .with("payment.processed");
    }

    @Bean
    public Binding paymentFailedBinding() {
        return BindingBuilder
                .bind(billetterieQueue())
                .to(eventExchange())
                .with("payment.failed");
    }
}
