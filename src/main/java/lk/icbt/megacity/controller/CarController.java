package lk.icbt.megacity.controller;

import lk.icbt.megacity.dto.CarDTO;
import lk.icbt.megacity.dto.request.CreateCarRequestDTO;
import lk.icbt.megacity.service.CarService;
import lk.icbt.megacity.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseUtil saveCar(@ModelAttribute CreateCarRequestDTO carRequestDTO) {

        CarDTO dto = new CarDTO();
        dto.setCategoryId(carRequestDTO.getCategoryId());
        dto.setCarName(carRequestDTO.getCarName());
        dto.setCarNumber(carRequestDTO.getCarNumber());

        CarDTO savedCar = carService.saveCar(dto, carRequestDTO.getCarImage());

        return ResponseUtil.builder()
                .status(HttpStatus.OK.value())
                .message("Car saved successfully")
                .data(savedCar)
                .build();
    }

}
