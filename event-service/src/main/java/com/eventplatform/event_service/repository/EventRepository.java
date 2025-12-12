package com.eventplatform.event_service.repository;

import com.eventplatform.event_service.model.Event;
import com.eventplatform.event_service.model.EventCategory;
import com.eventplatform.event_service.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour la gestion de la persistance des événements.
 * Fournit les opérations CRUD et des requêtes personnalisées pour la recherche.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Trouve tous les événements d'un organisateur
     */
    List<Event> findByOrganisateurId(Long organisateurId);

    /**
     * Trouve les événements par statut
     */
    List<Event> findByStatut(EventStatus statut);

    /**
     * Trouve les événements par catégorie
     */
    List<Event> findByCategorie(EventCategory categorie);

    /**
     * Recherche avancée avec filtres multiples
     */
    @Query("SELECT e FROM Event e WHERE " +
           "(:categorie IS NULL OR e.categorie = :categorie) AND " +
           "(:statut IS NULL OR e.statut = :statut) AND " +
           "(:dateDebut IS NULL OR e.dateDebut >= :dateDebut) AND " +
           "(:dateFin IS NULL OR e.dateFin <= :dateFin) AND " +
           "(:lieu IS NULL OR LOWER(e.lieu) LIKE LOWER(CONCAT('%', :lieu, '%'))) AND " +
           "(:titre IS NULL OR LOWER(e.titre) LIKE LOWER(CONCAT('%', :titre, '%')))")
    List<Event> searchEvents(
        @Param("categorie") EventCategory categorie,
        @Param("statut") EventStatus statut,
        @Param("dateDebut") LocalDateTime dateDebut,
        @Param("dateFin") LocalDateTime dateFin,
        @Param("lieu") String lieu,
        @Param("titre") String titre
    );

    /**
     * Trouve les événements à venir (publiés et non commencés)
     */
    @Query("SELECT e FROM Event e WHERE e.statut = 'PUBLIE' AND e.dateDebut > :now ORDER BY e.dateDebut ASC")
    List<Event> findUpcomingEvents(@Param("now") LocalDateTime now);

    /**
     * Trouve les événements en cours
     */
    @Query("SELECT e FROM Event e WHERE e.statut = 'EN_COURS' OR " +
           "(e.statut = 'PUBLIE' AND e.dateDebut <= :now AND e.dateFin >= :now)")
    List<Event> findOngoingEvents(@Param("now") LocalDateTime now);

    /**
     * Trouve les événements par période
     */
    @Query("SELECT e FROM Event e WHERE e.dateDebut BETWEEN :debut AND :fin ORDER BY e.dateDebut ASC")
    List<Event> findEventsBetweenDates(@Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin);

    /**
     * Compte les événements d'un organisateur par statut
     */
    Long countByOrganisateurIdAndStatut(Long organisateurId, EventStatus statut);
}
