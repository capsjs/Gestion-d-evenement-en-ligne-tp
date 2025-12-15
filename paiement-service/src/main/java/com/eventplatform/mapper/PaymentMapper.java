package com.eventplatform.paiement_service.mapper;

import com.eventplatform.paiement_service.DTO.PaymentResponseDTO;
import com.eventplatform.paiement_service.entities.Payment;

@Component
public class PaymentMapper {
    public PaymentResponseDTO toDTO(Payment payment) {

        if (payment == null) {
            return null;
        }

        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setBookingId(payment.getBookingId());
        dto.setAmount(payment.getAmount());
        dto.setCurrency(payment.getCurrency());
        dto.setStatus(payment.getStatus());
        dto.setProcessedAt(payment.getCreatedAt());
        return dto;
    }
}