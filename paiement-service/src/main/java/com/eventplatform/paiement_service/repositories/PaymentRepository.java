package com.eventplatform.paiement_service.repository;

import com.eventplatform.paiement_service.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByBookingId(String bookingId);
    boolean existsByBookingId(String bookingId);
}