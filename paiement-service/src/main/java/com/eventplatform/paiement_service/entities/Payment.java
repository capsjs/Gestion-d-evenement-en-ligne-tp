package com.eventplatform.paiement_service.entities;

import jakarta.persistence.*;
import lombok.Data; // <--- C'est lui qui génère les getters !
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data // Génère getters, setters, toString, etc.
@NoArgsConstructor // Constructeur vide obligatoire pour JPA
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String bookingId;

    private BigDecimal amount;
    
    private String currency;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String transactionReference;
    
    private LocalDateTime createdAt;
}