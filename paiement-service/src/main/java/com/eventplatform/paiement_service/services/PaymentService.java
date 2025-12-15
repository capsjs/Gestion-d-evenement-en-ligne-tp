package com.eventplatform.paiement_service.service;

import com.eventplatform.paiement_service.entities.Payment;
import com.eventplatform.paiement_service.entities.PaymentStatus; // Ou supprime si String
import com.eventplatform.paiement_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    // On commente temporairement la gateway et les events pour que ça compile d'abord
    // private final PaymentGateway paymentGateway;
    // private final EventPublisher eventPublisher; 

    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    public Payment getPaymentByBookingId(String bookingId) {
        return repository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}