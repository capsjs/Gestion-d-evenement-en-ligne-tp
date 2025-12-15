package com.eventplatform.billetterie_service.controller;

import com.eventplatform.billetterie_service.model.Ticket;
import com.eventplatform.billetterie_service.service.BilletterieService;
import org.springframework.web.bind.annotation.*;
import com.eventplatform.billetterie_service.dto.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class BilletterieController {

    private final BilletterieService billetterieService;

    public BilletterieController(BilletterieService billetterieService) {
        this.billetterieService = billetterieService;
    }

    @PostMapping("/book")
    public Ticket bookTicket(@RequestBody BookTicketRequest req) {
        return billetterieService.bookTicket(
                req.getEventId(),
                req.getUserId(),
                req.getType(),
                req.getPrix()
        );
    }

    @PostMapping("/{ticketId}/cancel")
    public Ticket cancel(@PathVariable UUID ticketId) {
        return billetterieService.cancelTicket(ticketId);
    }
}
