package lk.icbt.megacity.repo;

import lk.icbt.megacity.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepo extends JpaRepository<Car,Integer> {

    boolean existsByCarNumber(String carNumber);
}
