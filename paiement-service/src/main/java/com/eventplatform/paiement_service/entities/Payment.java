package com.event.payment.domain;

import jakarta.persistence.*;
import lombok.Data; // Utilise Lombok pour alléger le code (Getters/Setters)
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lien avec la réservation (Venant du service Billetterie)
    @Column(unique = true, nullable = false)
    private String bookingId; 

    private BigDecimal amount;
    
    private String currency; // "EUR", "USD"

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // PENDING, APPROVED, REJECTED, REFUNDED

    private String transactionReference; // ID renvoyé par PayPal/Stripe

    private LocalDateTime createdAt;
    
    // Constructeur par défaut requis par JPA
    public Payment() {} 
}