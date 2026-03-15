package lk.icbt.megacity.repo;

import lk.icbt.megacity.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepo extends JpaRepository<Booking,Integer> {

    List<Booking> findByUserId(int userId);
    List<Booking> findByStatus(String status);
}
