package com.eventplatform.paiement_service.controllers;

import com.eventplatform.paiement_service.entities.Payment;
import com.eventplatform.paiement_service.service.PaymentService;
import com.eventplatform.paiement_service.web.dto.PaymentResponseDTO;
import com.eventplatform.paiement_service.web.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        List<PaymentResponseDTO> dtos = payments.stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<PaymentResponseDTO> getPayment(@PathVariable String bookingId) {
        Payment payment = paymentService.getPaymentByBookingId(bookingId);
        return ResponseEntity.ok(paymentMapper.toDTO(payment));
    }
}