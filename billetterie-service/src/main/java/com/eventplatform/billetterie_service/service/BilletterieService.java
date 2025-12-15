package com.eventplatform.billetterie_service.service;

import com.eventplatform.billetterie_service.model.Ticket;
import com.eventplatform.billetterie_service.dto.TicketBookedEvent;
import com.eventplatform.billetterie_service.dto.TicketCancelledEvent;
import com.eventplatform.billetterie_service.messaging.TicketEventPublisher;
import com.eventplatform.billetterie_service.model.EtatTicket;
import com.eventplatform.billetterie_service.model.EventSeat;
import com.eventplatform.billetterie_service.model.TypeTicket;
import com.eventplatform.billetterie_service.repository.TicketRepository;
import com.eventplatform.billetterie_service.repository.EventSeatRepository;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BilletterieService {

    private final TicketRepository ticketRepository;
    private final EventSeatRepository eventSeatRepository;
    private final TicketEventPublisher ticketEventPublisher;

    public BilletterieService(
            TicketRepository ticketRepository,
            EventSeatRepository eventSeatRepository,
            TicketEventPublisher ticketEventPublisher
    ) {
        this.ticketRepository = ticketRepository;
        this.eventSeatRepository = eventSeatRepository;
        this.ticketEventPublisher = ticketEventPublisher;
    }

    @Transactional
    public Ticket bookTicket(UUID eventId, UUID userId, TypeTicket type, Float prix) {

        // Vérifier places disponibles
        EventSeat seat = eventSeatRepository.findById(eventId)
                        .orElseThrow(() -> new RuntimeException("Evenement non trouvé"));

        if (seat.getRemainingSeats() <= 0) {
                throw new RuntimeException("Plus de places disponibles");
        }

        seat.setRemainingSeats(seat.getRemainingSeats() - 1);
        eventSeatRepository.save(seat);

        // Création de ticket
        Ticket ticket = new Ticket(eventId, userId, type, prix);
        ticket = ticketRepository.save(ticket);

        // Event
        ticketEventPublisher.publishTicketBooked(
                new TicketBookedEvent(
                        ticket.getId(),
                        ticket.getEventId(),
                        ticket.getUserId(),
                        ticket.getPrix()
                )
        );

        return ticket;
    }

    @Transactional
    public Ticket cancelTicket(UUID ticketId) {
        Ticket t = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        EtatTicket etat = t.getEtat();
        if (etat == EtatTicket.UTILISE || etat == EtatTicket.EXPIRE || etat == EtatTicket.ANNULE) {
            throw new RuntimeException("Ticket dans un mauvais état");
        }

        t.setEtat(EtatTicket.ANNULE);
        t = ticketRepository.save(t);

        UUID eventId = t.getEventId();
        EventSeat seat = eventSeatRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evenement non trouvé"));
        seat.setRemainingSeats(seat.getRemainingSeats() + 1);

        // EVENT
        ticketEventPublisher.publishTicketCancelled(
                new TicketCancelledEvent(
                        t.getId(),
                        t.getEventId(),
                        t.getUserId()));

        return t;
    }

    @Transactional
    public void confirmTicket(UUID ticketId) {
    Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new RuntimeException("Ticket not found"));

    // On accepte la confirmation seulement si le ticket est réservé
    if (ticket.getEtat() != EtatTicket.RESERVE) {
        throw new RuntimeException("Ticket non confirmable");
    }

    ticket.setEtat(EtatTicket.PAYE);
    ticketRepository.save(ticket);
}

}
