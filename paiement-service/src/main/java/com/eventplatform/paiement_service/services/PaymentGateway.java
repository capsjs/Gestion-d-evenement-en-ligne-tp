package com.eventplatform.paiement_service.services;

import com.eventplatform.paiement_service.entities.PaymentStatus;
import java.math.BigDecimal;

public interface PaymentGateway {
    
    PaymentResult process(BigDecimal amount, String currency);

    // On définit le record ici pour simplifier
    record PaymentResult(String transactionId, PaymentStatus status) {}
}