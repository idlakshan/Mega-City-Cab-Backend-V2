package lk.icbt.megacity.repo;

import lk.icbt.megacity.dto.projection.CarWithCategoryProjection;
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
    Car getCarByCarId(int id);
    @Query(value = """
    SELECT 
        c.car_id AS carId,
        c.car_name AS carName,
        c.car_number AS carNumber,
        c.car_image AS carImage,
        c.status AS status,
        cat.id AS categoryId,
        cat.name AS categoryName
    FROM cars c
    JOIN categories cat ON c.category_id = cat.id
    WHERE c.car_id = :id
""", nativeQuery = true)
    CarWithCategoryProjection getCarWithCategoryByCarId(@Param("id") Integer id);
}
