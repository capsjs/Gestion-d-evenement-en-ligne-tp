package com.eventplatform.paiement_service.services;

import com.eventplatform.paiement_service.entities.Payment;
import java.util.List;
import java.util.UUID; 

public interface PaymentService {
    
    List<Payment> getAllPayments();
    
    // On passe bien un UUID ici
    Payment getPaymentByBookingId(UUID bookingId); 
    
    Payment createPayment(Payment payment);
}