@Service
@Transactional // Très important : Si ça plante, on annule tout en base
@RequiredArgsConstructor // Lombok pour l'injection de dépendance par constructeur
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentGateway paymentGateway;
    private final EventPublisher eventPublisher; // Ton wrapper RabbitMQ/Kafka

    public void handleTicketBooking(TicketBookedEvent event) {
        // 1. Vérifier si déjà payé (Idempotence)
        if (repository.existsByBookingId(event.getBookingId())) {
            return; // Déjà traité
        }

        // 2. Créer l'objet paiement
        Payment payment = new Payment();
        payment.setBookingId(event.getBookingId());
        payment.setAmount(event.getPrice());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PENDING);
        
        repository.save(payment);

        // 3. Appel à la "Banque"
        PaymentResult result = paymentGateway.process(payment.getAmount(), "EUR");
        
        // 4. Mise à jour statut
        payment.setStatus(result.getStatus());
        payment.setTransactionReference(result.getTransactionId());
        repository.save(payment);

        // 5. Envoyer l'événement de résultat
        if (payment.getStatus() == PaymentStatus.APPROVED) {
            eventPublisher.publish(new PaymentProcessedEvent(payment.getBookingId(), payment.getId()));
        } else {
            eventPublisher.publish(new PaymentFailedEvent(payment.getBookingId(), "Fonds insuffisants"));
        }
    }
}