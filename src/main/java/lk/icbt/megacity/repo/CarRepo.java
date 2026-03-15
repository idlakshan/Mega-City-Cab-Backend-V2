package lk.icbt.megacity.repo;

import lk.icbt.megacity.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepo extends JpaRepository<Car,Integer> {

    boolean existsByCarNumber(String carNumber);

    @Query(value = "SELECT * FROM cars WHERE category_id = :categoryId AND status = 'Available'", nativeQuery = true)
    List<Car> getAvailableCarsByCategory(@Param("categoryId") Integer categoryId);
    int countByStatus(String status);
}
