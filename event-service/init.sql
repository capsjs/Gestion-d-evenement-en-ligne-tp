-- Script d'initialisation de la base de données event_db
-- Création de 25 événements avec TOUS les statuts pour tester l'Event-Driven Architecture

-- Insertion des événements
INSERT INTO events (titre, description, date_debut, date_fin, lieu, categorie, statut, capacite_max, places_disponibles, organisateur_id, image_url, date_creation, date_modification) VALUES
-- CONCERTS (6 événements - différents statuts)
('Festival Rock en Seine 2026', 'Le plus grand festival de rock de France avec plus de 100 artistes internationaux', '2026-08-20 18:00:00', '2026-08-23 02:00:00', 'Paris, Domaine National de Saint-Cloud', 'CONCERT', 'PUBLIE', 120000, 85000, 1, 'https://example.com/rock-en-seine.jpg', NOW(), NOW()),
('Concert de Jazz à l''Olympia', 'Soirée jazz exceptionnelle avec les plus grands noms du jazz mondial', '2026-03-15 20:30:00', '2026-03-15 23:30:00', 'Paris, L''Olympia', 'CONCERT', 'PUBLIE', 2000, 1500, 1, 'https://example.com/jazz-olympia.jpg', NOW(), NOW()),
('Nuit Électro 2026', 'Festival de musique électronique avec les meilleurs DJs du monde', '2025-12-15 21:00:00', '2025-12-16 06:00:00', 'Lyon, Halle Tony Garnier', 'CONCERT', 'EN_COURS', 15000, 12000, 2, 'https://example.com/nuit-electro.jpg', NOW(), NOW()),
('Concert Classique - Philharmonie', 'Orchestre symphonique de Paris joue Beethoven et Mozart', '2025-11-05 19:00:00', '2025-11-05 21:30:00', 'Paris, Philharmonie', 'CONCERT', 'TERMINE', 2400, 0, 3, 'https://example.com/classique.jpg', NOW(), NOW()),
('Festival Hip-Hop Urban', 'Les légendes du hip-hop français et américain réunies', '2026-07-12 17:00:00', '2026-07-12 23:00:00', 'Marseille, Vélodrome', 'CONCERT', 'BROUILLON', 60000, 60000, 2, 'https://example.com/hip-hop.jpg', NOW(), NOW()),
('Concert Metal Extreme ANNULÉ', 'Annulé pour raisons de sécurité', '2026-06-20 19:00:00', '2026-06-20 23:00:00', 'Paris, Zénith', 'CONCERT', 'ANNULE', 5000, 5000, 2, 'https://example.com/metal.jpg', NOW(), NOW()),

-- CONFÉRENCES (5 événements - différents statuts)
('Web Summit Paris 2026', 'La plus grande conférence tech d''Europe - 70 000 participants attendus', '2026-05-20 09:00:00', '2026-05-23 18:00:00', 'Paris, Porte de Versailles', 'CONFERENCE', 'PUBLIE', 70000, 45000, 4, 'https://example.com/web-summit.jpg', NOW(), NOW()),
('Conférence IA & Innovation', 'L''intelligence artificielle au service de l''innovation - Conférenciers internationaux', '2026-02-18 09:00:00', '2026-02-18 17:00:00', 'Lyon, Centre des Congrès', 'CONFERENCE', 'PUBLIE', 3000, 2100, 4, 'https://example.com/ia-conference.jpg', NOW(), NOW()),
('Startup Weekend', 'Créez votre startup en 54 heures - Bootcamp intensif pour entrepreneurs', '2026-03-27 18:00:00', '2026-03-29 20:00:00', 'Bordeaux, Darwin Ecosystem', 'CONFERENCE', 'PUBLIE', 150, 80, 5, 'https://example.com/startup-weekend.jpg', NOW(), NOW()),
('Forum Climat & Développement Durable', 'Rencontre internationale sur les enjeux climatiques', '2026-09-15 09:00:00', '2026-09-17 18:00:00', 'Strasbourg, Palais de la Musique', 'CONFERENCE', 'BROUILLON', 5000, 5000, 6, 'https://example.com/forum-climat.jpg', NOW(), NOW()),
('Tech Conference 2025 ANNULÉE', 'Conférence annulée - reports à 2026', '2025-11-10 09:00:00', '2025-11-10 18:00:00', 'Paris, La Défense', 'CONFERENCE', 'ANNULE', 1000, 1000, 4, 'https://example.com/tech-annule.jpg', NOW(), NOW()),

-- FORMATIONS (4 événements - différents statuts)
('Formation Java Spring Boot Avancé', 'Maîtrisez Spring Boot et les microservices - Formation intensive de 5 jours', '2026-01-20 09:00:00', '2026-01-24 17:00:00', 'Paris, Le Wagon', 'FORMATION', 'PUBLIE', 30, 12, 7, 'https://example.com/spring-formation.jpg', NOW(), NOW()),
('Workshop React & TypeScript', 'Développez des applications web modernes avec React et TypeScript', '2025-12-15 09:00:00', '2025-12-16 17:00:00', 'Lyon, Wild Code School', 'FORMATION', 'EN_COURS', 25, 8, 7, 'https://example.com/react-workshop.jpg', NOW(), NOW()),
('Certification AWS Cloud Practitioner', 'Préparation intensive à la certification AWS - 3 jours', '2025-10-05 09:00:00', '2025-10-07 17:00:00', 'Toulouse, Simplon.co', 'FORMATION', 'TERMINE', 20, 0, 8, 'https://example.com/aws-certification.jpg', NOW(), NOW()),
('Formation Docker & Kubernetes', 'Maîtrisez la conteneurisation et l''orchestration', '2026-02-15 09:00:00', '2026-02-18 17:00:00', 'Paris, OpenClassrooms', 'FORMATION', 'BROUILLON', 20, 20, 7, 'https://example.com/docker.jpg', NOW(), NOW()),

-- SPORT (5 événements - différents statuts)
('Marathon de Paris 2026', 'La 50ème édition du marathon de Paris - Parcours mythique', '2026-04-12 08:00:00', '2026-04-12 15:00:00', 'Paris, Champs-Élysées', 'SPORT', 'PUBLIE', 50000, 15000, 9, 'https://example.com/marathon-paris.jpg', NOW(), NOW()),
('Finale Coupe de France Football', 'PSG vs OM - Le classique français au Stade de France', '2026-05-08 21:00:00', '2026-05-08 23:00:00', 'Paris, Stade de France', 'SPORT', 'PUBLIE', 80000, 0, 10, 'https://example.com/coupe-france.jpg', NOW(), NOW()),
('Tournoi de Tennis Roland-Garros', 'Les internationaux de France de tennis - Billets journée', '2026-05-24 11:00:00', '2026-05-24 20:00:00', 'Paris, Roland-Garros', 'SPORT', 'PUBLIE', 15000, 3000, 9, 'https://example.com/roland-garros.jpg', NOW(), NOW()),
('Match PSG vs Lyon EN DIRECT', 'Ligue 1 - Match en cours au Parc des Princes', '2025-12-15 21:00:00', '2025-12-15 23:00:00', 'Paris, Parc des Princes', 'SPORT', 'EN_COURS', 48000, 0, 10, 'https://example.com/psg-lyon.jpg', NOW(), NOW()),
('Match France vs Brésil ANNULÉ', 'Match amical annulé pour raisons sanitaires', '2025-11-15 21:00:00', '2025-11-15 23:00:00', 'Paris, Stade de France', 'SPORT', 'ANNULE', 80000, 80000, 10, 'https://example.com/france-bresil.jpg', NOW(), NOW()),

-- THÉÂTRE (3 événements - différents statuts)
('Molière - Le Misanthrope', 'Pièce classique de Molière interprétée par la Comédie-Française', '2026-02-05 20:00:00', '2026-02-05 22:30:00', 'Paris, Comédie-Française', 'THEATRE', 'PUBLIE', 862, 200, 12, 'https://example.com/moliere.jpg', NOW(), NOW()),
('Cyrano de Bergerac', 'La célèbre pièce d''Edmond Rostand - Mise en scène moderne', '2025-12-15 19:30:00', '2025-12-15 22:00:00', 'Lyon, Théâtre des Célestins', 'THEATRE', 'EN_COURS', 750, 0, 12, 'https://example.com/cyrano.jpg', NOW(), NOW()),
('Roméo et Juliette TERMINÉ', 'Représentation exceptionnelle qui s''est terminée hier', '2025-12-10 20:00:00', '2025-12-10 22:30:00', 'Paris, Théâtre Mogador', 'THEATRE', 'TERMINE', 1800, 0, 12, 'https://example.com/romeo.jpg', NOW(), NOW()),

-- EXPOSITIONS (2 événements)
('Exposition Van Gogh Immersive', 'Plongez dans l''univers de Van Gogh grâce à la réalité virtuelle', '2026-01-15 10:00:00', '2026-04-30 19:00:00', 'Paris, Atelier des Lumières', 'EXPOSITION', 'PUBLIE', 500, 300, 13, 'https://example.com/van-gogh.jpg', NOW(), NOW()),
('Salon du Livre 2026', 'Le plus grand salon du livre en France - Rencontres avec 200 auteurs', '2026-03-20 10:00:00', '2026-03-23 19:00:00', 'Paris, Porte de Versailles', 'EXPOSITION', 'PUBLIE', 100000, 75000, 13, 'https://example.com/salon-livre.jpg', NOW(), NOW());

-- Vérification du nombre d'événements insérés
SELECT COUNT(*) as nombre_total_evenements FROM events;

-- Affichage des événements par catégorie
SELECT categorie, COUNT(*) as nombre 
FROM events 
GROUP BY categorie 
ORDER BY categorie;

-- 🎯 Affichage CRUCIAL : événements par statut (pour Event-Driven Testing)
SELECT statut, COUNT(*) as nombre 
FROM events 
GROUP BY statut 
ORDER BY statut;

-- Statistiques détaillées
SELECT 
    '📊 STATISTIQUES DES ÉVÉNEMENTS' as info,
    (SELECT COUNT(*) FROM events WHERE statut = 'BROUILLON') as brouillon,
    (SELECT COUNT(*) FROM events WHERE statut = 'PUBLIE') as publie,
    (SELECT COUNT(*) FROM events WHERE statut = 'EN_COURS') as en_cours,
    (SELECT COUNT(*) FROM events WHERE statut = 'TERMINE') as termine,
    (SELECT COUNT(*) FROM events WHERE statut = 'ANNULE') as annule,
    COUNT(*) as total
FROM events;
