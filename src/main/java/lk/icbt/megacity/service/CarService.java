package lk.icbt.megacity.service;

import lk.icbt.megacity.dto.CarDTO;
import org.springframework.web.multipart.MultipartFile;

public interface CarService {
    CarDTO saveCar(CarDTO dto, MultipartFile file);

}
