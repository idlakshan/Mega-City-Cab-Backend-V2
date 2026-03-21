package lk.icbt.megacity.repo;


import lk.icbt.megacity.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Integer> {
    boolean existsByDriverNic(String driverNic);

    @Query(value = "SELECT * FROM drivers WHERE status = 'Available'", nativeQuery = true)
    List<Driver> getAvailableDrivers();
    int countByStatus(String status);
    Driver getDriverByDriverId(int id);
}
