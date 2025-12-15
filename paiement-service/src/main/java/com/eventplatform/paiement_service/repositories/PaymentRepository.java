package com.eventplatform.paiement_service.repositories;

import com.eventplatform.paiement_service.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByBookingId(UUID bookingId);
    boolean existsByBookingId(UUID bookingId);
}