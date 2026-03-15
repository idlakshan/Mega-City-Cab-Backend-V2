package lk.icbt.megacity.service;

import lk.icbt.megacity.dto.CarDTO;
import lk.icbt.megacity.dto.request.CreateCarRequestDTO;

import java.util.List;

public interface CarService {
    CarDTO saveCar(CreateCarRequestDTO carRequestDTO);
    List<CarDTO> getAvailableCarsByCategory(Integer categoryId);
    void updateCarStatus(CarDTO dto);

    int getAvailableVehicles();

    List<CarDTO> getAllCars();

}
