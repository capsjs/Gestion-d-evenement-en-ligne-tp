package com.eventplatform.notificationservice.dto.incoming;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentProcessedIncoming(
        String bookingId,
        BigDecimal amount,
        String currency,
        String status,
        LocalDateTime processedAt
) {}
