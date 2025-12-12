package com.eventplatform.billetterie_service.repository;

import com.eventplatform.billetterie_service.model.Ticket;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
}
