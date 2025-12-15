package com.eventplatform.paiement_service.web.mapper;

import com.eventplatform.paiement_service.entities.Payment;
import com.eventplatform.paiement_service.web.dto.PaymentResponseDTO;
import org.springframework.stereotype.Component;

@Component // Important pour que Spring puisse l'injecter dans le Controller
public class PaymentMapper {

    public PaymentResponseDTO toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }
        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .bookingId(payment.getBookingId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}