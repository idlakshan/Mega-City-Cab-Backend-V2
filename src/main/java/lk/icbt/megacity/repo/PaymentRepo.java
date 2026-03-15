package lk.icbt.megacity.repo;

import lk.icbt.megacity.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {
    @Query("SELECT p FROM Payment p WHERE p.booking.user.id = :userId ORDER BY p.paymentDate ASC")
    List<Payment> findPaymentsByUserId(Integer userId);
}

