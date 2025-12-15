package com.eventplatform.paiement_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID; 

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID bookingId; // ✅ UUID ici aussi
    
    private BigDecimal amount;
    
    private String currency;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // Assure-toi que l'Enum existe
    
    private LocalDateTime createdAt;
}