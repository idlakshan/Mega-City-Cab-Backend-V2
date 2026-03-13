package lk.icbt.megacity.service;

import lk.icbt.megacity.dto.CarDTO;
import lk.icbt.megacity.dto.request.CreateCarRequestDTO;

public interface CarService {
    CarDTO saveCar(CreateCarRequestDTO carRequestDTO);

}
