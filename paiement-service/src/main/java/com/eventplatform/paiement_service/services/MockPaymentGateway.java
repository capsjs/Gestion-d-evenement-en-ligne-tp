package com.eventplatform.paiement_service.services;

import com.eventplatform.paiement_service.entities.PaymentStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class MockPaymentGateway implements PaymentGateway {

    @Override
    public PaymentResult process(BigDecimal amount, String currency) {
        // Simulation : on génère un faux ID de transaction
        return new PaymentResult("TX_" + UUID.randomUUID(), PaymentStatus.APPROVED);
    }
}