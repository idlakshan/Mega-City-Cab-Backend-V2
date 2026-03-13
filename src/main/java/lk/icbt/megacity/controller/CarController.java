package lk.icbt.megacity.controller;

import lk.icbt.megacity.dto.CarDTO;
import lk.icbt.megacity.dto.request.CreateCarRequestDTO;
import lk.icbt.megacity.service.CarService;
import lk.icbt.megacity.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> saveCar(@ModelAttribute CreateCarRequestDTO carRequestDTO) {

        CarDTO carDTO = carService.saveCar(carRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).
                body(new ResponseUtil(200, "Car saved successfully", carDTO));
    }

}
