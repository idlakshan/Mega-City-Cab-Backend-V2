package lk.icbt.megacity.repo;

import lk.icbt.megacity.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Integer> {
    boolean existsByDriverNic(String driverNic);
}
