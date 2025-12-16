package com.eventplatform.event_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Service Événements.
 * 
 * Ce microservice gère le catalogue d'événements, leur cycle de vie,
 * et publie des événements métier pour la communication asynchrone
 * avec les autres services (Billetterie, Paiement, Notifications).
 * 
 * Architecture: Microservices Event-Driven
 * Base de données: PostgreSQL
 * Message Broker: RabbitMQ
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class EventServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventServiceApplication.class, args);
	}

}
