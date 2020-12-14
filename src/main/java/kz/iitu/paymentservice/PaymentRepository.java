package kz.iitu.paymentservice;

import org.springframework.data.jpa.repository.JpaRepository;
import kz.iitu.paymentservice.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
