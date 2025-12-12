package com.eventplatform.billetterie_service.service;

import com.eventplatform.billetterie_service.model.Ticket;
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

    public BilletterieService(TicketRepository ticketRepository, EventSeatRepository eventSeatRepository) {
        this.ticketRepository = ticketRepository;
        this.eventSeatRepository = eventSeatRepository;
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

        //Création de ticket
        Ticket ticket = new Ticket(eventId, userId, type, prix);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket cancelTicket(UUID ticketId) {
        Ticket t = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        t.setEtat(EtatTicket.ANNULE);
        return ticketRepository.save(t);
    }
}
