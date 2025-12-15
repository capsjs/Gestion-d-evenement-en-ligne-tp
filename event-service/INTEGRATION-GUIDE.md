# 🚀 Event Service - Guide Rapide pour les autres services

## 📌 Résumé Exécutif

Le **Event Service** expose des endpoints REST et publie des événements RabbitMQ pour la communication inter-services.

**Port**: `8081`  
**Base URL**: `http://localhost:8081`

---

## 🎯 Pour BILLETTERIE-SERVICE

### Endpoints REST à appeler

```http
# 1. Vérifier qu'un événement existe et a des places
GET http://localhost:8081/api/events/{eventId}

# 2. Réduire les places lors d'une réservation
POST http://localhost:8081/api/events/internal/{eventId}/decrease-seats?quantity=5

# 3. Libérer les places lors d'une annulation
POST http://localhost:8081/api/events/internal/{eventId}/increase-seats?quantity=5
```

### Événements RabbitMQ à écouter

**Queue**: `event.updated.queue` (Routing key: `event.updated`)
```java
@RabbitListener(queues = "event.updated.queue")
public void handleEventUpdated(EventUpdatedEvent event) {
    // Mettre à jour les billets existants
    // Payload: eventId, titre, dateDebut, dateFin, lieu, changementsDescription
}
```

**Queue**: `event.cancelled.queue` (Routing key: `event.cancelled`)
```java
@RabbitListener(queues = "event.cancelled.queue")
public void handleEventCancelled(EventCancelledEvent event) {
    // Annuler toutes les réservations de cet événement
    // Payload: eventId, titre, raisonAnnulation, organisateurId
}
```

---

## 💰 Pour PAIEMENT-SERVICE

### Événements RabbitMQ à écouter

**Queue**: `event.cancelled.queue` (Routing key: `event.cancelled`)
```java
@RabbitListener(queues = "event.cancelled.queue")
public void handleEventCancelled(EventCancelledEvent event) {
    // Déclencher les remboursements pour cet événement
    // Payload: eventId, titre, raisonAnnulation, organisateurId
}
```

---

## 🔔 Pour NOTIFICATION-SERVICE

### Événements RabbitMQ à écouter

**Queue**: `event.created.queue` (Routing key: `event.created`)
```java
@RabbitListener(queues = "event.created.queue")
public void handleEventCreated(EventCreatedEvent event) {
    // Notifier les utilisateurs intéressés par cette catégorie
    // Payload: eventId, titre, description, dateDebut, dateFin, lieu, categorie, organisateurId
}
```

**Queue**: `event.updated.queue` (Routing key: `event.updated`)
```java
@RabbitListener(queues = "event.updated.queue")
public void handleEventUpdated(EventUpdatedEvent event) {
    // Notifier les participants de la modification
    // Payload: eventId, titre, dateDebut, dateFin, lieu, changementsDescription
}
```

**Queue**: `event.cancelled.queue` (Routing key: `event.cancelled`)
```java
@RabbitListener(queues = "event.cancelled.queue")
public void handleEventCancelled(EventCancelledEvent event) {
    // Notifier tous les participants de l'annulation
    // Payload: eventId, titre, raisonAnnulation, organisateurId
}
```

---

## 👤 Pour USER-SERVICE

### Endpoints REST à appeler

```http
# Récupérer tous les événements d'un organisateur
GET http://localhost:8081/api/events/search?organisateurId={userId}
```

---

## ⚙️ Configuration RabbitMQ (à ajouter dans application.properties)

```properties
# Connexion RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# Ajouter la dépendance dans pom.xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

---

## 📦 Classes d'événements à copier dans votre projet

### EventCreatedEvent.java
```java
package com.eventplatform.{votre_service}.dto.events;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventCreatedEvent implements Serializable {
    private Long eventId;
    private String titre;
    private String description;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String lieu;
    private String categorie; // CONCERT, CONFERENCE, FORMATION, SPORT, THEATRE, EXPOSITION, FESTIVAL
    private Long organisateurId;
    private LocalDateTime timestamp;
}
```

### EventUpdatedEvent.java
```java
package com.eventplatform.{votre_service}.dto.events;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventUpdatedEvent implements Serializable {
    private Long eventId;
    private String titre;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String lieu;
    private String changementsDescription;
    private LocalDateTime timestamp;
}
```

### EventCancelledEvent.java
```java
package com.eventplatform.{votre_service}.dto.events;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventCancelledEvent implements Serializable {
    private Long eventId;
    private String titre;
    private String raisonAnnulation;
    private Long organisateurId;
    private LocalDateTime timestamp;
}
```

---

## 🧪 Comment tester ?

### 1. Vérifier que RabbitMQ fonctionne
- Ouvrir http://localhost:15672 (guest/guest)
- Vérifier que l'exchange `event.exchange` existe
- Vérifier que vos queues sont créées et bindées

### 2. Tester la réception d'événements

**Déclencher un EventCreatedEvent:**
```bash
curl -X POST http://localhost:8081/api/events/1/publish
```

**Déclencher un EventUpdatedEvent:**
```bash
curl -X PUT http://localhost:8081/api/events/1 \
  -H "Content-Type: application/json" \
  -d '{"titre": "Titre modifié"}'
```

**Déclencher un EventCancelledEvent:**
```bash
curl -X POST http://localhost:8081/api/events/1/cancel \
  -H "Content-Type: application/json" \
  -d '{"reason": "Test annulation"}'
```

### 3. Vérifier les logs de votre listener
Vous devriez voir dans vos logs :
```
Événement créé reçu: 1
Événement mis à jour reçu: 1
Événement annulé reçu: 1
```

---

## 📞 Support

**Documentation complète**: Voir `API-DOCUMENTATION.md`  
**Fichier de tests**: `event-service.http`  

**Questions?** Contactez l'équipe Event Service

---

**Date**: 15 Décembre 2025
