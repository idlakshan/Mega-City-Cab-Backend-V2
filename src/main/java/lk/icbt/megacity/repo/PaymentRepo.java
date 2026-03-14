package lk.icbt.megacity.repo;

import lk.icbt.megacity.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {

}

