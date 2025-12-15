package com.eventplatform.paiement_service.services;

import com.eventplatform.paiement_service.entities.Payment;
import com.eventplatform.paiement_service.entities.PaymentStatus; 
import com.eventplatform.paiement_service.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime; // Import Date
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment createPayment(Payment payment) {
        // 🛡️ Règle métier 1 : On initialise la date nous-mêmes
        payment.setCreatedAt(LocalDateTime.now());
        
        // 🛡️ Règle métier 2 : Si le statut n'est pas fourni, on met PENDING par défaut
        if (payment.getStatus() == null) {
            payment.setStatus(PaymentStatus.PENDING);
        }
        
        // 🛡️ Règle métier 3 : Vérification (optionnel mais recommandé)
        if (payment.getBookingId() == null) {
            throw new IllegalArgumentException("Le paiement doit être lié à un bookingId !");
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentByBookingId(UUID bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}