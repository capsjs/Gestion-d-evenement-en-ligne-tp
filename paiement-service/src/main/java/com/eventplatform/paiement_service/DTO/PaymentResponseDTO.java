package com.eventplatform.paiement_service.web.dto;

import com.eventplatform.paiement_service.entities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class PaymentResponseDTO {
    private UUID id;
    private UUID bookingId; // Ou String, selon ce que tu veux montrer au front
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private LocalDateTime createdAt;
}