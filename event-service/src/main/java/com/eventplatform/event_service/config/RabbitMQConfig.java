package com.eventplatform.event_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration RabbitMQ pour l'architecture Event-Driven.
 * Définit les exchanges, queues et bindings pour la communication inter-services.
 */
@Configuration
public class RabbitMQConfig {

    // Noms des exchanges
    public static final String EVENT_EXCHANGE = "event.exchange";
    
    // Noms des queues
    public static final String EVENT_CREATED_QUEUE = "event.created.queue";
    public static final String EVENT_UPDATED_QUEUE = "event.updated.queue";
    public static final String EVENT_CANCELLED_QUEUE = "event.cancelled.queue";
    
    // Routing keys
    public static final String EVENT_CREATED_ROUTING_KEY = "event.created";
    public static final String EVENT_UPDATED_ROUTING_KEY = "event.updated";
    public static final String EVENT_CANCELLED_ROUTING_KEY = "event.cancelled";

    /**
     * Exchange principal pour tous les événements métier
     */
    @Bean
    public TopicExchange eventExchange() {
        return new TopicExchange(EVENT_EXCHANGE);
    }

    /**
     * Queue pour les événements de création
     */
    @Bean
    public Queue eventCreatedQueue() {
        return QueueBuilder.durable(EVENT_CREATED_QUEUE)
                .withArgument("x-dead-letter-exchange", "dlx.exchange")
                .build();
    }

    /**
     * Queue pour les événements de mise à jour
     */
    @Bean
    public Queue eventUpdatedQueue() {
        return QueueBuilder.durable(EVENT_UPDATED_QUEUE)
                .withArgument("x-dead-letter-exchange", "dlx.exchange")
                .build();
    }

    /**
     * Queue pour les événements d'annulation
     */
    @Bean
    public Queue eventCancelledQueue() {
        return QueueBuilder.durable(EVENT_CANCELLED_QUEUE)
                .withArgument("x-dead-letter-exchange", "dlx.exchange")
                .build();
    }

    /**
     * Bindings entre l'exchange et les queues
     */
    @Bean
    public Binding eventCreatedBinding() {
        return BindingBuilder
                .bind(eventCreatedQueue())
                .to(eventExchange())
                .with(EVENT_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding eventUpdatedBinding() {
        return BindingBuilder
                .bind(eventUpdatedQueue())
                .to(eventExchange())
                .with(EVENT_UPDATED_ROUTING_KEY);
    }

    @Bean
    public Binding eventCancelledBinding() {
        return BindingBuilder
                .bind(eventCancelledQueue())
                .to(eventExchange())
                .with(EVENT_CANCELLED_ROUTING_KEY);
    }

    /**
     * Convertisseur JSON pour la sérialisation des messages
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate configuré avec le convertisseur JSON
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
