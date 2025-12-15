package com.eventplatform.billetterie_service.repository;

import com.eventplatform.billetterie_service.model.EventSeat;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventSeatRepository extends JpaRepository<EventSeat, UUID> {
}
