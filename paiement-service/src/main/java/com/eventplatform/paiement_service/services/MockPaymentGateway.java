// Interface
public interface PaymentGateway {
    PaymentResult process(BigDecimal amount, String currency);
}

// Implémentation Mock (Simulation pour le prof)
@Service
public class MockPaymentGateway implements PaymentGateway {
    @Override
    public PaymentResult process(BigDecimal amount, String currency) {
        // Simulation : 90% de chance de succès, 10% d'échec (pour tester les bugs)
        if (Math.random() > 0.1) {
            return new PaymentResult("TX_" + UUID.randomUUID(), PaymentStatus.APPROVED);
        } else {
            return new PaymentResult(null, PaymentStatus.REJECTED);
        }
    }
}