# 🎯 GUIDE D'IMPLÉMENTATION - PLATEFORME DE GESTION D'ÉVÉNEMENTS EN LIGNE

## 📋 Table des matières

1. [Vue d'ensemble du projet](#vue-densemble-du-projet)
2. [Architecture](#architecture)
3. [Prérequis](#prérequis)
4. [Installation et démarrage](#installation-et-démarrage)
5. [Services microservices](#services-microservices)
6. [API Gateway](#api-gateway)
7. [Communication inter-services](#communication-inter-services)
8. [Base de données](#base-de-données)
9. [Tests des APIs](#tests-des-apis)
10. [Dépannage](#dépannage)
11. [Architecture technique détaillée](#architecture-technique-détaillée)

---

## 🎯 Vue d'ensemble du projet

### Description

Plateforme complète de gestion d'événements en ligne permettant de :
- ✅ Créer et gérer des événements (concerts, conférences, formations, etc.)
- ✅ Gérer les utilisateurs et leur authentification
- ✅ Réserver des billets pour les événements
- ✅ Traiter les paiements
- ✅ Envoyer des notifications automatiques

### Technologies principales

- **Backend** : Java 21, Spring Boot 3.x, Spring Cloud
- **Base de données** : PostgreSQL 15
- **Messaging** : RabbitMQ 3
- **API Gateway** : Spring Cloud Gateway MVC
- **Containerisation** : Docker, Docker Compose
- **Architecture** : Microservices, Event-Driven Architecture

---

## 🏗️ Architecture

### Architecture microservices (6 services)

```
┌─────────────────────────────────────────────────────────────┐
│                        CLIENT                               │
│              (Browser, Mobile App, Postman)                 │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
              ┌──────────────────────┐
              │   API GATEWAY :8080  │  Point d'entrée unique
              │  (Spring Cloud GW)   │
              └──────────┬───────────┘
                         │
          ┌──────────────┼──────────────┬─────────────┬────────────┐
          │              │              │             │            │
          ▼              ▼              ▼             ▼            ▼
    ┌─────────┐    ┌─────────┐    ┌──────────┐  ┌─────────┐  ┌──────────┐
    │  User   │    │  Event  │    │Billette  │  │Paiement │  │Notificat.│
    │ :8081   │    │ :8084   │    │  :8082   │  │ :8083   │  │  :8085   │
    └────┬────┘    └────┬────┘    └────┬─────┘  └────┬────┘  └────┬─────┘
         │              │              │             │            │
         ▼              ▼              ▼             ▼            ▼
    ┌─────────┐    ┌─────────┐    ┌──────────┐  ┌─────────┐
    │postgres │    │postgres │    │postgres  │  │postgres │
    │ :5435   │    │ :5433   │    │  :5432   │  │ :5434   │
    └─────────┘    └─────────┘    └──────────┘  └─────────┘
                         │
                         └──────────┬─────────────────┐
                                    ▼                 │
                            ┌──────────────┐          │
                            │   RabbitMQ   │◀─────────┘
                            │ :5672/:15672 │
                            └──────────────┘
```

### Containers Docker (11 au total)

| Container | Type | Port(s) | Description |
|-----------|------|---------|-------------|
| **api-gateway** | Service | 8080 | Point d'entrée unique, routage intelligent |
| **user-service** | Service | 8081 | Gestion des utilisateurs |
| **billetterie-service** | Service | 8082 | Gestion des billets |
| **paiement-service** | Service | 8083 | Traitement des paiements |
| **event-service** | Service | 8084 | Gestion des événements (cœur) |
| **notification-service** | Service | 8085 | Envoi de notifications |
| **postgres_users** | Database | 5435 | Base de données utilisateurs |
| **postgres_billetterie** | Database | 5432 | Base de données billetterie |
| **postgres_payment** | Database | 5434 | Base de données paiements |
| **postgres_event** | Database | 5433 | Base de données événements |
| **rabbitmq** | Infrastructure | 5672, 15672 | Messaging asynchrone |

---

## 📦 Prérequis

### Logiciels requis

- **Docker Desktop** : Version 4.x ou supérieure
- **Docker Compose** : Version 2.x (inclus avec Docker Desktop)
- **Java 21** (optionnel, pour développement local)
- **Maven 3.9+** (optionnel, pour développement local)
- **Git** : Pour cloner le repository
- **Un client REST** : Postman, Insomnia, ou curl
- **jq** (optionnel) : Pour formatter les réponses JSON

### Vérification de l'installation

```bash
# Vérifier Docker
docker --version
# Attendu: Docker version 24.x ou supérieur

# Vérifier Docker Compose
docker compose version
# Attendu: Docker Compose version v2.x ou supérieur

# Vérifier Java (optionnel)
java --version
# Attendu: openjdk 21.x

# Vérifier Maven (optionnel)
mvn --version
# Attendu: Apache Maven 3.9.x
```

---

## 🚀 Installation et démarrage

### 1. Cloner le repository

```bash
git clone <repository-url>
cd Gestion-d-evenement-en-ligne-tp
```

### 2. Premier démarrage (build et démarrage)

```bash
# Construire toutes les images Docker et démarrer tous les services
docker compose up --build -d

# Attendre que tous les services soient prêts (20-30 secondes)
sleep 30

# Vérifier que tous les services sont UP
docker compose ps
```

### 3. Vérification du démarrage

```bash
# Tous les containers doivent être "Up"
docker compose ps

# Exemple de sortie attendue:
# NAME                   STATUS          PORTS
# api_gateway            Up 2 minutes    0.0.0.0:8080->8080/tcp
# user_service           Up 2 minutes    0.0.0.0:8081->8081/tcp
# billetterie_service    Up 2 minutes    0.0.0.0:8082->8082/tcp
# paiement_service       Up 2 minutes    0.0.0.0:8083->8083/tcp
# event_service          Up 2 minutes    0.0.0.0:8084->8084/tcp
# notification_service   Up 2 minutes    0.0.0.0:8085->8085/tcp
# postgres_users         Up 2 minutes    0.0.0.0:5435->5432/tcp
# postgres_billetterie   Up 2 minutes    0.0.0.0:5432->5432/tcp
# postgres_payment       Up 2 minutes    0.0.0.0:5434->5432/tcp
# postgres_event         Up 2 minutes    0.0.0.0:5433->5432/tcp
# rabbitmq               Up 2 minutes    0.0.0.0:5672->5672/tcp, 0.0.0.0:15672->15672/tcp
```

### 4. Vérifier les logs

```bash
# Voir les logs de tous les services
docker compose logs -f

# Voir les logs d'un service spécifique
docker compose logs -f event-service

# Voir les dernières 50 lignes d'un service
docker compose logs --tail=50 api-gateway
```

### 5. Test rapide

```bash
# Tester l'API Gateway
curl http://localhost:8080/api/events | jq

# Devrait retourner 25 événements
```

---

## 🎯 Services microservices

### 1. EVENT-SERVICE (Port 8084) - Cœur du système

**Responsabilité** : Gestion complète du cycle de vie des événements

#### Fonctionnalités

- ✅ CRUD complet des événements
- ✅ Gestion des statuts (BROUILLON, PUBLIE, EN_COURS, TERMINE, ANNULE)
- ✅ Gestion des catégories (CONCERT, CONFERENCE, FORMATION, SPORT, THEATRE, EXPOSITION)
- ✅ Recherche et filtrage avancés
- ✅ Gestion des places disponibles
- ✅ Publication d'événements RabbitMQ

#### API Endpoints

```bash
# Récupérer tous les événements
GET http://localhost:8080/api/events

# Récupérer un événement par ID
GET http://localhost:8080/api/events/{id}

# Créer un événement (statut BROUILLON)
POST http://localhost:8080/api/events
Content-Type: application/json
{
  "titre": "Concert Rock 2026",
  "description": "Un concert exceptionnel",
  "lieu": "Paris, Stade de France",
  "dateDebut": "2026-06-15T20:00:00",
  "dateFin": "2026-06-15T23:00:00",
  "capaciteMax": 80000,
  "categorie": "CONCERT",
  "organisateurId": 1,
  "imageUrl": "https://example.com/concert.jpg"
}

# Publier un événement (BROUILLON → PUBLIE)
PUT http://localhost:8080/api/events/{id}/publish

# Annuler un événement
PUT http://localhost:8080/api/events/{id}/cancel

# Modifier un événement
PUT http://localhost:8080/api/events/{id}

# Rechercher par statut
GET http://localhost:8080/api/events/search?statut=PUBLIE

# Rechercher par catégorie
GET http://localhost:8080/api/events/search?categorie=CONCERT

# Événements à venir
GET http://localhost:8080/api/events/upcoming

# API interne (utilisée par billetterie-service)
POST http://event-service:8084/api/events/internal/{id}/decrease-seats?quantity=5
POST http://event-service:8084/api/events/internal/{id}/increase-seats?quantity=3
```

#### Base de données

- **Database** : `event_db` (PostgreSQL sur port 5433)
- **Table** : `events` (14 colonnes)
- **Données initiales** : 25 événements (via `init.sql`)

#### Technologies

- Spring Boot 3.x
- Spring Data JPA / Hibernate
- PostgreSQL 15
- Spring AMQP (RabbitMQ)
- Validation API

#### Configuration

```properties
# application.properties
server.port=8084
spring.datasource.url=jdbc:postgresql://postgres_event:5432/event_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.rabbitmq.host=rabbitmq
spring.rabbitmq.port=5672
```

---

### 2. USER-SERVICE (Port 8081)

**Responsabilité** : Gestion des utilisateurs et authentification

#### Fonctionnalités

- ✅ Création d'utilisateurs
- ✅ Authentification
- ✅ Gestion de profils
- ✅ Rôles et permissions

#### API Endpoints

```bash
# Récupérer tous les utilisateurs
GET http://localhost:8080/api/users

# Créer un utilisateur
POST http://localhost:8080/api/users
Content-Type: application/json
{
  "nom": "Dupont",
  "prenom": "Jean",
  "email": "jean.dupont@example.com",
  "motDePasse": "password123"
}

# Récupérer un utilisateur par ID
GET http://localhost:8080/api/users/{id}
```

#### Base de données

- **Database** : `users_db` (PostgreSQL sur port 5435)
- **Table** : `users`

---

### 3. BILLETTERIE-SERVICE (Port 8082)

**Responsabilité** : Gestion des réservations de billets

#### Fonctionnalités

- ✅ Réservation de billets
- ✅ Annulation de billets
- ✅ Vérification de disponibilité (via Event-Service)
- ✅ Écoute des événements RabbitMQ (annulation d'événement)

#### API Endpoints

```bash
# Réserver un billet
POST http://localhost:8080/api/tickets/book
Content-Type: application/json
{
  "eventId": 1,
  "userId": 1,
  "type": "VIP",
  "prix": 150.0
}

# Annuler un billet
POST http://localhost:8080/api/tickets/{ticketId}/cancel
```

#### Base de données

- **Database** : `billetteriedb` (PostgreSQL sur port 5432)
- **Table** : `tickets`

#### Communication

- **REST (Synchrone)** : Appelle Event-Service pour diminuer/augmenter les places
- **RabbitMQ (Asynchrone)** : Écoute les événements d'annulation

---

### 4. PAIEMENT-SERVICE (Port 8083)

**Responsabilité** : Traitement des paiements

#### Fonctionnalités

- ✅ Traitement des paiements
- ✅ Remboursements
- ✅ Historique des transactions

#### API Endpoints

```bash
# Créer un paiement
POST http://localhost:8080/api/payments
Content-Type: application/json
{
  "montant": 150.0,
  "utilisateurId": 1,
  "ticketId": "uuid-ticket-id",
  "methodePaiement": "CARTE_CREDIT"
}

# Récupérer tous les paiements
GET http://localhost:8080/api/payments
```

#### Base de données

- **Database** : `payment_db` (PostgreSQL sur port 5434)
- **Table** : `payments`

---

### 5. NOTIFICATION-SERVICE (Port 8085)

**Responsabilité** : Envoi de notifications

#### Fonctionnalités

- ✅ Envoi d'emails
- ✅ Notifications SMS (à implémenter)
- ✅ Écoute des événements RabbitMQ (création, modification, annulation)

#### Communication

- **RabbitMQ (Asynchrone)** : Écoute les événements d'Event-Service

#### Queues RabbitMQ écoutées

- `queue.event.created` : Nouvel événement publié
- `queue.event.updated` : Événement modifié
- `queue.event.cancelled` : Événement annulé

---

### 6. API-GATEWAY (Port 8080)

**Responsabilité** : Point d'entrée unique, routage intelligent

#### Fonctionnalités

- ✅ Routage des requêtes vers les microservices
- ✅ Load balancing
- ✅ Sécurité centralisée (à implémenter : JWT)
- ✅ Monitoring

#### Routes configurées

```yaml
routes:
  - /api/users/**        → user-service:8081
  - /api/events/**       → event-service:8084
  - /api/tickets/**      → billetterie-service:8082
  - /api/payments/**     → paiement-service:8083
  - /api/notifications/** → notification-service:8085
```

#### Avantages

- Point d'entrée unique : `http://localhost:8080`
- Abstraction des microservices
- Gestion centralisée de la sécurité
- Facilité de monitoring

---

## 🔄 Communication inter-services

### 1. Communication SYNCHRONE (REST)

**Utilisation** : Opérations critiques nécessitant une réponse immédiate

**Exemple** : Billetterie-Service → Event-Service

```java
// Billetterie-Service appelle Event-Service pour diminuer les places
POST http://event-service:8084/api/events/internal/{eventId}/decrease-seats?quantity=5

// Event-Service répond immédiatement
200 OK
{
  "id": 1,
  "titre": "Festival Rock en Seine",
  "placesDisponibles": 84995
}
```

**Avantages** :
- Réponse immédiate
- Garantie que l'opération a réussi

**Inconvénients** :
- Couplage entre services
- Si Event-Service est down, l'opération échoue

---

### 2. Communication ASYNCHRONE (RabbitMQ)

**Utilisation** : Notifications, tâches de fond, pas besoin de réponse

#### Architecture RabbitMQ

```
Event-Service (Publisher)
    ↓
RabbitMQ Exchange: "events.exchange"
    ↓
Queues (Files d'attente)
    ├─→ queue.event.created → Notification-Service
    ├─→ queue.event.updated → Notification-Service
    └─→ queue.event.cancelled → Notification-Service + Billetterie-Service
```

#### Exemple : Annulation d'un événement

```
1. Event-Service publie un message RabbitMQ
   Topic: "event.cancelled"
   Message: {
     "eventId": 6,
     "titre": "Concert Metal Extreme",
     "reason": "Annulé pour raisons de sécurité"
   }

2. Notification-Service reçoit le message
   → Envoie des emails à tous les participants

3. Billetterie-Service reçoit le message
   → Rembourse automatiquement tous les billets
```

**Avantages** :
- Découplage complet des services
- Résilience (messages persistés dans RabbitMQ)
- Scalabilité (facile d'ajouter de nouveaux listeners)
- Asynchrone (pas d'attente)

---

## 🗄️ Base de données

### Architecture des bases de données

Chaque microservice a sa **propre base de données PostgreSQL** (principe des microservices).

| Service | Database | Port | Tables principales |
|---------|----------|------|-------------------|
| user-service | users_db | 5435 | users |
| event-service | event_db | 5433 | events |
| billetterie-service | billetteriedb | 5432 | tickets |
| paiement-service | payment_db | 5434 | payments |

### Initialisation des données

#### Event-Service : 25 événements initiaux

Le fichier `event-service/init.sql` contient 25 événements répartis sur tous les statuts :

```sql
-- 5 événements BROUILLON
-- 8 événements PUBLIE
-- 5 événements EN_COURS
-- 5 événements TERMINE
-- 2 événements ANNULE
```

**Catégories** :
- CONCERT (8 événements)
- CONFERENCE (5 événements)
- FORMATION (4 événements)
- SPORT (4 événements)
- THEATRE (2 événements)
- EXPOSITION (2 événements)

### Connexion aux bases de données

```bash
# Se connecter à event_db
docker exec -it postgres_event psql -U postgres -d event_db

# Exemples de requêtes SQL
SELECT COUNT(*) FROM events;
SELECT titre, statut, categorie FROM events WHERE statut = 'PUBLIE';
SELECT * FROM events WHERE categorie = 'CONCERT';

# Quitter
\q
```

---

## 🧪 Tests des APIs

### Via API Gateway (Point d'entrée unique)

#### Test Event-Service

```bash
# Récupérer tous les événements (devrait retourner 25)
curl http://localhost:8080/api/events | jq 'length'

# Récupérer un événement spécifique
curl http://localhost:8080/api/events/1 | jq

# Rechercher par statut PUBLIE
curl "http://localhost:8080/api/events/search?statut=PUBLIE" | jq

# Rechercher par catégorie CONCERT
curl "http://localhost:8080/api/events/search?categorie=CONCERT" | jq

# Événements à venir
curl http://localhost:8080/api/events/upcoming | jq

# Créer un nouvel événement
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "titre": "Festival Jazz 2026",
    "description": "Festival de jazz en plein air",
    "lieu": "Lyon, Parc de la Tête d Or",
    "dateDebut": "2026-07-01T18:00:00",
    "dateFin": "2026-07-01T23:00:00",
    "capaciteMax": 50000,
    "categorie": "CONCERT",
    "organisateurId": 1,
    "imageUrl": "https://example.com/jazz.jpg"
  }' | jq

# Publier un événement (changer ID 26 par l'ID créé)
curl -X PUT http://localhost:8080/api/events/26/publish | jq
```

#### Test User-Service

```bash
# Récupérer tous les utilisateurs
curl http://localhost:8080/api/users | jq

# Créer un utilisateur
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Martin",
    "prenom": "Sophie",
    "email": "sophie.martin@example.com",
    "motDePasse": "password123"
  }' | jq
```

#### Test Billetterie-Service

```bash
# Réserver un billet pour l'événement ID 1
curl -X POST http://localhost:8080/api/tickets/book \
  -H "Content-Type: application/json" \
  -d '{
    "eventId": 1,
    "userId": 1,
    "type": "VIP",
    "prix": 150.0
  }' | jq
```

### Via RabbitMQ Management UI

```bash
# Ouvrir l'interface RabbitMQ
open http://localhost:15672

# Credentials
Username: guest
Password: guest
```

**Vérifications** :
- ✅ Exchange `events.exchange` créé
- ✅ Queues `queue.event.created`, `queue.event.updated`, `queue.event.cancelled`
- ✅ Messages publiés et consommés

---

## 🛠️ Dépannage

### Problème 1 : Port déjà utilisé

**Erreur** :
```
Bind for 0.0.0.0:8080 failed: port is already allocated
```

**Solution** :
```bash
# Arrêter tous les containers Docker
docker stop $(docker ps -aq)

# Ou trouver le processus qui utilise le port
lsof -i :8080
kill -9 <PID>
```

---

### Problème 2 : Service ne démarre pas

**Erreur** :
```
UnresolvedAddressException: user-service
```

**Solution** :
```bash
# Vérifier que tous les services sont UP
docker compose ps

# Si un service est manquant, voir les logs
docker compose logs <service-name>

# Redémarrer le service
docker compose restart <service-name>
```

---

### Problème 3 : Incompatibilité de version PostgreSQL

**Erreur** :
```
database files are incompatible with server
The data directory was initialized by PostgreSQL version 16, 
which is not compatible with this version 15
```

**Solution** :
```bash
# Arrêter tous les services
docker compose down

# Supprimer le volume corrompu
docker volume rm gestion-d-evenement-en-ligne-tp_postgres_users_data

# Redémarrer
docker compose up -d
```

---

### Problème 4 : RabbitMQ Connection Refused

**Erreur** :
```
AmqpConnectException: Connection refused
```

**Solution** :
```bash
# Vérifier que RabbitMQ tourne
docker compose ps rabbitmq

# Si down, redémarrer
docker compose restart rabbitmq

# Attendre 10 secondes
sleep 10

# Redémarrer les services qui dépendent de RabbitMQ
docker compose restart event-service notification-service billetterie-service
```

---

### Problème 5 : Initialisation des 25 événements

**Symptôme** : La base `event_db` est vide

**Solution** :
```bash
# Supprimer le volume postgres_event
docker compose down
docker volume rm gestion-d-evenement-en-ligne-tp_postgres_event_data

# Redémarrer (le script init.sql sera exécuté)
docker compose up -d postgres_event

# Attendre 10 secondes
sleep 10

# Vérifier
docker exec -it postgres_event psql -U postgres -d event_db -c "SELECT COUNT(*) FROM events;"
# Devrait retourner 25
```

---

## 📊 Architecture technique détaillée

### Stack technologique par service

#### Event-Service
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
</dependencies>
```

#### API Gateway
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway-mvc</artifactId>
    </dependency>
</dependencies>
```

### Dockerisation multi-stage

Tous les services utilisent un **Dockerfile multi-stage** pour optimiser la taille des images :

```dockerfile
# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Avantages** :
- Image finale légère (~150 MB vs 600 MB)
- Sécurité (pas de Maven, pas de code source dans l'image finale)
- Build reproductible

---

## 🔐 Sécurité (À implémenter)

### Authentification JWT (Recommandé)

```yaml
# À implémenter dans API Gateway
spring:
  cloud:
    gateway:
      server:
        webmvc:
          filters:
            - name: JWT
              args:
                secret: your-secret-key
```

---

## 📈 Monitoring (À implémenter)

### Spring Boot Actuator

Tous les services exposent des endpoints Actuator :

```bash
# Health check
curl http://localhost:8084/actuator/health

# Metrics
curl http://localhost:8084/actuator/metrics
```

---

## 🚀 Déploiement en production

### Kubernetes (Recommandé)

```yaml
# Exemple de déploiement Kubernetes
apiVersion: apps/v1
kind: Deployment
metadata:
  name: event-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: event-service
  template:
    metadata:
      labels:
        app: event-service
    spec:
      containers:
      - name: event-service
        image: event-service:latest
        ports:
        - containerPort: 8084
```

---

## 📚 Commandes utiles

### Docker Compose

```bash
# Démarrer tous les services
docker compose up -d

# Arrêter tous les services
docker compose down

# Rebuild et redémarrer
docker compose up --build -d

# Voir les logs
docker compose logs -f

# Voir les logs d'un service
docker compose logs -f event-service

# Redémarrer un service
docker compose restart event-service

# Voir l'état des containers
docker compose ps

# Supprimer tout (containers + volumes)
docker compose down -v
```

### Docker

```bash
# Voir tous les containers
docker ps -a

# Voir tous les volumes
docker volume ls

# Supprimer un volume
docker volume rm <volume-name>

# Voir les images
docker images

# Supprimer une image
docker rmi <image-id>

# Nettoyer tout
docker system prune -a --volumes
```

---

## 🎓 Évolutions possibles

### Fonctionnalités

- [ ] Authentification JWT complète
- [ ] Circuit Breaker (Resilience4j)
- [ ] Service Discovery (Eureka)
- [ ] Elasticsearch pour la recherche avancée
- [ ] Cache distribué (Redis)
- [ ] Rate limiting
- [ ] Logging centralisé (ELK Stack)
- [ ] Tracing distribué (Jaeger, Zipkin)

### Architecture

- [ ] Déploiement Kubernetes
- [ ] CI/CD avec GitHub Actions
- [ ] Tests d'intégration automatisés
- [ ] Load testing (JMeter, Gatling)
- [ ] Documentation API (Swagger/OpenAPI)

---

## 📞 Support

Pour toute question ou problème :

1. Consulter la section [Dépannage](#dépannage)
2. Vérifier les logs : `docker compose logs -f`
3. Vérifier l'état des services : `docker compose ps`

---

## 📄 Licence

Projet académique - Gestion d'événements en ligne

---

**Dernière mise à jour** : 16 décembre 2025

**Version** : 1.0.0

**Auteur** : E. Aubert
