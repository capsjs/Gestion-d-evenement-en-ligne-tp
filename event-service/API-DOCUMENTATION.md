# 📚 Event Service - Documentation API

## 📋 Table des matières
1. [Informations générales](#informations-générales)
2. [Endpoints REST](#endpoints-rest)
3. [Événements RabbitMQ](#événements-rabbitmq)
4. [Modèles de données](#modèles-de-données)
5. [Configuration RabbitMQ](#configuration-rabbitmq)
6. [Tests et validation](#tests-et-validation)

---

## 🌐 Informations générales

**Service**: Event Service  
**Port**: 8084  
**Base URL**: `http://localhost:8084`  
**Version**: 1.0.0  

### Technologies
- **Framework**: Spring Boot 3.x
- **Base de données**: PostgreSQL
- **Message Broker**: RabbitMQ
- **Documentation**: API REST + RabbitMQ Events

---

## 🔌 Endpoints REST

### 📌 Endpoints Publics

#### 1. Récupérer tous les événements
```http
GET /api/events
```
**Réponse**: Liste de tous les événements (200 OK)

---

#### 2. Récupérer un événement par ID
```http
GET /api/events/{eventId}
```
**Paramètres**:
- `eventId` (path) - ID de l'événement

**Réponse**: Détails de l'événement (200 OK)  
**Erreurs**: 
- 404 NOT FOUND - Événement non trouvé

---

#### 3. Créer un événement
```http
POST /api/events
Content-Type: application/json
```

**Body**:
```json
{
  "titre": "Concert de Jazz",
  "description": "Description de l'événement",
  "dateDebut": "2025-12-20T19:00:00",
  "dateFin": "2025-12-20T23:00:00",
  "lieu": "Paris, Bercy",
  "categorie": "CONCERT",
  "statut": "BROUILLON",
  "capaciteMax": 500,
  "organisateurId": 1,
  "imageUrl": "https://example.com/image.jpg"
}
```

**Catégories disponibles**: `CONCERT`, `CONFERENCE`, `FORMATION`, `SPORT`, `THEATRE`, `EXPOSITION`, `FESTIVAL`  
**Statuts disponibles**: `BROUILLON`, `PUBLIE`, `EN_COURS`, `TERMINE`, `ANNULE`

**Réponse**: Événement créé (201 CREATED)  
**Erreurs**:
- 400 BAD REQUEST - Données invalides
- 409 CONFLICT - Événement en doublon (même titre, date, lieu)

---

#### 4. Mettre à jour un événement
```http
PUT /api/events/{eventId}
Content-Type: application/json
```

**Body** (tous les champs sont optionnels):
```json
{
  "titre": "Nouveau titre",
  "description": "Nouvelle description",
  "dateDebut": "2025-12-20T19:00:00",
  "dateFin": "2025-12-20T23:00:00",
  "lieu": "Nouveau lieu",
  "capaciteMax": 600,
  "imageUrl": "https://example.com/new-image.jpg"
}
```

**⚠️ Déclenche**: `EventUpdatedEvent` vers RabbitMQ

**Réponse**: Événement mis à jour (200 OK)  
**Erreurs**:
- 404 NOT FOUND - Événement non trouvé
- 400 BAD REQUEST - Opération invalide (statut non modifiable)

---

#### 5. Publier un événement
```http
POST /api/events/{eventId}/publish
```

**⚠️ Déclenche**: `EventCreatedEvent` vers RabbitMQ

**Réponse**: Événement publié (200 OK)  
**Erreurs**:
- 404 NOT FOUND - Événement non trouvé
- 400 BAD REQUEST - Seuls les événements en BROUILLON peuvent être publiés

---

#### 6. Annuler un événement
```http
POST /api/events/{eventId}/cancel
Content-Type: application/json
```

**Body**:
```json
{
  "reason": "Raison de l'annulation"
}
```

**⚠️ Déclenche**: `EventCancelledEvent` vers RabbitMQ

**Réponse**: Événement annulé (200 OK)  
**Erreurs**:
- 404 NOT FOUND - Événement non trouvé
- 400 BAD REQUEST - L'événement ne peut pas être annulé

---

#### 7. Rechercher des événements
```http
GET /api/events/search?categorie={categorie}&statut={statut}&organisateurId={id}
```

**Paramètres de requête** (tous optionnels):
- `categorie` - Filtrer par catégorie
- `statut` - Filtrer par statut
- `organisateurId` - Filtrer par organisateur
- `lieu` - Recherche par lieu (partielle)
- `titre` - Recherche par titre (partielle)
- `dateDebut` - Événements après cette date
- `dateFin` - Événements avant cette date

**Exemple**:
```http
GET /api/events/search?categorie=CONCERT&statut=PUBLIE
```

**Réponse**: Liste d'événements filtrés (200 OK)

---

#### 8. Événements à venir
```http
GET /api/events/upcoming
```

**Réponse**: Liste des événements publiés et à venir (200 OK)

---

#### 9. Événements en cours
```http
GET /api/events/ongoing
```

**Réponse**: Liste des événements en cours (200 OK)

---

### 🔒 Endpoints Internes (Inter-services)

> **⚠️ Ces endpoints sont destinés aux communications entre microservices uniquement**

#### 10. Réduire les places disponibles
```http
POST /api/events/internal/{eventId}/decrease-seats?quantity={quantity}
```

**Utilisé par**: `billetterie-service`  
**Cas d'usage**: Lors de la création d'une réservation

**Paramètres**:
- `eventId` (path) - ID de l'événement
- `quantity` (query) - Nombre de places à réserver

**Réponse**: 200 OK  
**Erreurs**:
- 404 NOT FOUND - Événement non trouvé
- 400 BAD REQUEST - Pas assez de places disponibles

---

#### 11. Augmenter les places disponibles
```http
POST /api/events/internal/{eventId}/increase-seats?quantity={quantity}
```

**Utilisé par**: `billetterie-service`  
**Cas d'usage**: Lors de l'annulation d'une réservation

**Paramètres**:
- `eventId` (path) - ID de l'événement
- `quantity` (query) - Nombre de places à libérer

**Réponse**: 200 OK  
**Erreurs**:
- 404 NOT FOUND - Événement non trouvé

---

## 📨 Événements RabbitMQ

### Configuration RabbitMQ

**Exchange**: `event.exchange` (type: `topic`)

**Queues et Routing Keys**:

| Queue | Routing Key | Événement déclenché |
|-------|-------------|---------------------|
| `event.created.queue` | `event.created` | Lors de la publication d'un événement |
| `event.updated.queue` | `event.updated` | Lors de la modification d'un événement |
| `event.cancelled.queue` | `event.cancelled` | Lors de l'annulation d'un événement |

---

### 1. EventCreatedEvent

**Déclenché**: Lorsqu'un événement passe de BROUILLON à PUBLIE  
**Routing Key**: `event.created`  
**Services concernés**: 
- ✅ **notification-service** (notifications aux utilisateurs)

**Payload**:
```json
{
  "eventId": 1,
  "titre": "Concert de Jazz",
  "description": "Description de l'événement",
  "dateDebut": "2025-12-20T19:00:00",
  "dateFin": "2025-12-20T23:00:00",
  "lieu": "Paris, Bercy",
  "categorie": "CONCERT",
  "organisateurId": 1,
  "timestamp": "2025-12-15T10:30:00"
}
```

**Actions attendues**:
- **notification-service**: Envoyer des notifications aux utilisateurs intéressés par cette catégorie

---

### 2. EventUpdatedEvent

**Déclenché**: Lorsqu'un événement est modifié (PUT /api/events/{id})  
**Routing Key**: `event.updated`  
**Services concernés**: 
- ✅ **billetterie-service** (mise à jour des réservations)
- ✅ **notification-service** (notifications aux participants)

**Payload**:
```json
{
  "eventId": 1,
  "titre": "Concert de Jazz - COMPLET",
  "dateDebut": "2025-12-20T19:00:00",
  "dateFin": "2025-12-20T23:00:00",
  "lieu": "Paris, AccorHotels Arena",
  "changementsDescription": "Changement de lieu et titre",
  "timestamp": "2025-12-15T11:00:00"
}
```

**Actions attendues**:
- **billetterie-service**: Mettre à jour les informations des billets existants
- **notification-service**: Envoyer des notifications aux participants concernés

---

### 3. EventCancelledEvent

**Déclenché**: Lorsqu'un événement est annulé (POST /api/events/{id}/cancel)  
**Routing Key**: `event.cancelled`  
**Services concernés**: 
- ✅ **billetterie-service** (annulation des réservations)
- ✅ **paiement-service** (remboursements)
- ✅ **notification-service** (notifications aux participants)

**Payload**:
```json
{
  "eventId": 1,
  "titre": "Concert de Jazz",
  "raisonAnnulation": "Annulé pour raisons météorologiques",
  "organisateurId": 1,
  "timestamp": "2025-12-15T12:00:00"
}
```

**Actions attendues**:
- **billetterie-service**: Annuler toutes les réservations de cet événement
- **paiement-service**: Déclencher les remboursements pour tous les paiements liés
- **notification-service**: Envoyer des notifications d'annulation à tous les participants

---

## 📦 Modèles de données

### EventResponse

```json
{
  "id": 1,
  "titre": "Concert de Jazz",
  "description": "Un superbe concert de jazz",
  "dateDebut": "2025-12-20T19:00:00",
  "dateFin": "2025-12-20T23:00:00",
  "lieu": "Paris, Bercy",
  "categorie": "CONCERT",
  "statut": "PUBLIE",
  "capaciteMax": 500,
  "placesDisponibles": 450,
  "organisateurId": 1,
  "imageUrl": "https://example.com/image.jpg",
  "dateCreation": "2025-12-15T10:00:00",
  "dateModification": "2025-12-15T10:30:00",
  "isReservable": true,
  "isModifiable": true,
  "isAnnulable": true
}
```

### Catégories d'événements

```java
public enum EventCategory {
    CONCERT,
    CONFERENCE,
    FORMATION,
    SPORT,
    THEATRE,
    EXPOSITION,
    FESTIVAL
}
```

### Statuts d'événements

```java
public enum EventStatus {
    BROUILLON,    // Événement en cours de création
    PUBLIE,       // Événement publié et réservable
    EN_COURS,     // Événement en cours
    TERMINE,      // Événement terminé
    ANNULE        // Événement annulé
}
```

### Règles métier

| Statut | Modifiable | Annulable | Réservable |
|--------|-----------|-----------|------------|
| BROUILLON | ✅ Oui | ✅ Oui | ❌ Non |
| PUBLIE | ✅ Oui | ✅ Oui | ✅ Oui (si places disponibles) |
| EN_COURS | ❌ Non | ✅ Oui | ❌ Non |
| TERMINE | ❌ Non | ❌ Non | ❌ Non |
| ANNULE | ❌ Non | ❌ Non | ❌ Non |

---

## ⚙️ Configuration RabbitMQ

### Pour les développeurs des autres services

**Connexion RabbitMQ**:
```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

### Exemple de configuration pour écouter les événements (Spring Boot)

```java
@Configuration
public class RabbitMQConfig {
    
    public static final String EVENT_EXCHANGE = "event.exchange";
    
    // Pour notification-service
    public static final String EVENT_CREATED_QUEUE = "event.created.queue";
    public static final String EVENT_CREATED_ROUTING_KEY = "event.created";
    
    // Pour billetterie-service et notification-service
    public static final String EVENT_UPDATED_QUEUE = "event.updated.queue";
    public static final String EVENT_UPDATED_ROUTING_KEY = "event.updated";
    
    // Pour billetterie-service, paiement-service et notification-service
    public static final String EVENT_CANCELLED_QUEUE = "event.cancelled.queue";
    public static final String EVENT_CANCELLED_ROUTING_KEY = "event.cancelled";
    
    @Bean
    public TopicExchange eventExchange() {
        return new TopicExchange(EVENT_EXCHANGE);
    }
    
    // Exemple de binding pour notification-service
    @Bean
    public Queue eventCreatedQueue() {
        return new Queue(EVENT_CREATED_QUEUE);
    }
    
    @Bean
    public Binding eventCreatedBinding() {
        return BindingBuilder
            .bind(eventCreatedQueue())
            .to(eventExchange())
            .with(EVENT_CREATED_ROUTING_KEY);
    }
}
```

### Exemple de listener (Consumer)

```java
@Component
@Slf4j
public class EventListener {
    
    @RabbitListener(queues = "event.created.queue")
    public void handleEventCreated(EventCreatedEvent event) {
        log.info("Événement créé reçu: {}", event.getEventId());
        // Votre logique ici
    }
    
    @RabbitListener(queues = "event.updated.queue")
    public void handleEventUpdated(EventUpdatedEvent event) {
        log.info("Événement mis à jour reçu: {}", event.getEventId());
        // Votre logique ici
    }
    
    @RabbitListener(queues = "event.cancelled.queue")
    public void handleEventCancelled(EventCancelledEvent event) {
        log.info("Événement annulé reçu: {}", event.getEventId());
        // Votre logique ici
    }
}
```

---

## ✅ Tests et validation

### Vérifier RabbitMQ

1. **Interface de gestion RabbitMQ**:
   - URL: http://localhost:15672
   - Username: `guest`
   - Password: `guest`

2. **Vérifier les exchanges**:
   - Aller dans l'onglet "Exchanges"
   - Vous devriez voir `event.exchange`

3. **Vérifier les queues**:
   - Aller dans l'onglet "Queues"
   - Vous devriez voir:
     - `event.created.queue`
     - `event.updated.queue`
     - `event.cancelled.queue`

### Tester les endpoints

Un fichier de tests complet est disponible: `event-service.http`

**Tests rapides**:

```bash
# 1. Créer un événement
curl -X POST http://localhost:8084/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "titre": "Test Event",
    "description": "Test",
    "dateDebut": "2025-12-20T19:00:00",
    "dateFin": "2025-12-20T23:00:00",
    "lieu": "Paris",
    "categorie": "CONCERT",
    "statut": "BROUILLON",
    "capaciteMax": 100,
    "organisateurId": 1
  }'

# 2. Récupérer tous les événements
curl http://localhost:8084/api/events

# 3. Publier l'événement (déclenche RabbitMQ)
curl -X POST http://localhost:8084/api/events/1/publish
```

---

## 📞 Contact et support

**Équipe**: Event Service Team  
**Repository**: Gestion-d-evenement-en-ligne-tp/event-service  

### Points de contact par service

| Service | Endpoints utilisés | Événements écoutés |
|---------|-------------------|-------------------|
| **billetterie-service** | `GET /api/events/{id}`<br>`POST /internal/{id}/decrease-seats`<br>`POST /internal/{id}/increase-seats` | `event.updated`<br>`event.cancelled` |
| **paiement-service** | - | `event.cancelled` |
| **notification-service** | - | `event.created`<br>`event.updated`<br>`event.cancelled` |
| **user-service** | `GET /api/events/search?organisateurId={id}` | - |

---

## 🔄 Diagramme de flux

### Flux de création d'événement
```
1. Organisateur → POST /api/events (BROUILLON)
2. Organisateur → POST /api/events/{id}/publish
3. Event Service → RabbitMQ (EventCreatedEvent)
4. Notification Service ← RabbitMQ
5. Notification Service → Envoie notifications
```

### Flux d'annulation d'événement
```
1. Organisateur → POST /api/events/{id}/cancel
2. Event Service → RabbitMQ (EventCancelledEvent)
3. Billetterie Service ← RabbitMQ → Annule réservations
4. Paiement Service ← RabbitMQ → Déclenche remboursements
5. Notification Service ← RabbitMQ → Envoie notifications
```

---

**Dernière mise à jour**: 15 Décembre 2025  
**Version**: 1.0.0
