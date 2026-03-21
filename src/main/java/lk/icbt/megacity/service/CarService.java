package lk.icbt.megacity.service;

import lk.icbt.megacity.dto.CarDTO;
import lk.icbt.megacity.dto.request.CreateCarRequestDTO;
import lk.icbt.megacity.dto.request.UpdateCarRequestDTO;
import lk.icbt.megacity.dto.response.CarWithCategoryDTO;

import java.util.List;

public interface CarService {
    CarDTO saveCar(CreateCarRequestDTO carRequestDTO);
    List<CarDTO> getAvailableCarsByCategory(Integer categoryId);
    void updateCarStatus(CarDTO dto);

    int getAvailableVehicles();

    List<CarDTO> getAllCars();

    CarDTO getCarById(int id);

    CarWithCategoryDTO getCarWithCategoryByCarId(Integer id);

    CarDTO updateCar(UpdateCarRequestDTO updateCarRequestDTO);

    void deleteCar(Integer carId);
}
