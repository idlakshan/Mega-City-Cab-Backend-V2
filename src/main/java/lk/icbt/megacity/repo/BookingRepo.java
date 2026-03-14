package lk.icbt.megacity.repo;

import lk.icbt.megacity.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking,Integer> {

}
