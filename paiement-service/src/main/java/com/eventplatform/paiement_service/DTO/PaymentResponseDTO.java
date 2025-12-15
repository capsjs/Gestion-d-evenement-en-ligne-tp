package com.eventplatform.paiement_service.web.dto;

import com.eventplatform.paiement_service.entities.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDTO(
    String bookingId,
    BigDecimal amount,
    String currency,
    PaymentStatus status,
    LocalDateTime processedAt
) {}