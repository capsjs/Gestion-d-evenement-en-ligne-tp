package com.eventplatform.paiement_service.config;

import com.eventplatform.paiement_service.entities.Payment;
import com.eventplatform.paiement_service.entities.PaymentStatus;
import com.eventplatform.paiement_service.repositories.PaymentRepository; // Vérifie bien ce package (repository vs repositories)
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID; // Import nécessaire pour les UUID

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(PaymentRepository repository) {
        return args -> {
            // On ne crée des données que si la base est vide
            if (repository.count() == 0) {
                System.out.println("🚀 Initialisation des données de test...");

                Payment p1 = new Payment();
                // On génère un vrai UUID pour que PostgreSQL soit content
                p1.setBookingId(UUID.randomUUID()); 
                p1.setAmount(new BigDecimal("99.99"));
                p1.setCurrency("EUR");
                p1.setStatus(PaymentStatus.APPROVED);
                // p1.setTransactionReference("TX_REF_001"); // ⚠️ Je commente ça car le champ n'existe peut-être pas dans ton Entité
                p1.setCreatedAt(LocalDateTime.now());

                Payment p2 = new Payment();
                p2.setBookingId(UUID.randomUUID());
                p2.setAmount(new BigDecimal("45.50"));
                p2.setCurrency("EUR");
                p2.setStatus(PaymentStatus.PENDING);
                p2.setCreatedAt(LocalDateTime.now());

                repository.save(p1);
                repository.save(p2);
                
                System.out.println("✅ 2 Paiements de test insérés !");
            } else {
                System.out.println("ℹ️ La base contient déjà des données, pas d'insertion.");
            }
        };
    }
}