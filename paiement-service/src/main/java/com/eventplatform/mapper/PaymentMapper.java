package com.eventplatform.paiement_service.web.mapper;

import com.eventplatform.paiement_service.entities.Payment;
import com.eventplatform.paiement_service.web.dto.PaymentResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentResponseDTO toDTO(Payment entity) {
        if (entity == null) return null;
        return new PaymentResponseDTO(
            entity.getBookingId(),
            entity.getAmount(),
            entity.getCurrency(),
            entity.getStatus(),
            entity.getCreatedAt()
        );
    }
}