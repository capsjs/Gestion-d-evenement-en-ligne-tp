package com.eventplatform.paiement_service.DTO;

import com.eventplatform.paiement_service.entities.PaymentStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
    private Long id;
    private String bookingId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private LocalDateTime processedAt;
}
