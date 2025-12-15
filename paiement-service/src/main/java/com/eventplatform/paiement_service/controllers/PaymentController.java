package com.event.payment.web;

import com.event.payment.domain.Payment;
import com.event.payment.service.PaymentService;
import com.event.payment.web.dto.PaymentResponseDTO;
import com.event.payment.web.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
// @CrossOrigin("*") // A décommenter si Angular est sur un autre port en local
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    // 1. Récupérer un paiement spécifique (Pour la page "Confirmation de commande")
    @GetMapping("/{bookingId}")
    public ResponseEntity<PaymentResponseDTO> getPayment(@PathVariable String bookingId) {
        Payment payment = paymentService.getPaymentByBookingId(bookingId);
        return ResponseEntity.ok(paymentMapper.toDTO(payment));
    }

    // 2. Récupérer tous les paiements (Pour le Back-Office Admin)
    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        
        // On transforme la liste d'Entités en liste de DTOs
        List<PaymentResponseDTO> dtos = payments.stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}