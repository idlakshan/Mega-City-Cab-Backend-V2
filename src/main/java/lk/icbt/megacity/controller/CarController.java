package lk.icbt.megacity.controller;

import lk.icbt.megacity.dto.CarDTO;
import lk.icbt.megacity.dto.request.CreateCarRequestDTO;
import lk.icbt.megacity.dto.request.UpdateCarRequestDTO;
import lk.icbt.megacity.dto.response.CarWithCategoryDTO;
import lk.icbt.megacity.service.CarService;
import lk.icbt.megacity.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> saveCar(@ModelAttribute CreateCarRequestDTO carRequestDTO) {

        CarDTO carDTO = carService.saveCar(carRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(new ResponseUtil(201, "Car saved successfully", carDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> getAllCars() {
        List<CarDTO> allCars = carService.getAllCars();
        return ResponseEntity.status(HttpStatus.OK).
                body(new ResponseUtil(200, "Cars retrieved successfully", allCars));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> getCarById(@PathVariable Integer id){
        CarDTO car = carService.getCarById(id);
        return ResponseEntity.status(HttpStatus.OK).
                body(new ResponseUtil(200, "Car retrieved successfully", car));
    }

    @GetMapping("/{id}/with-category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> getCarWithCategoryByCarId(@PathVariable Integer id) {

        CarWithCategoryDTO car = carService.getCarWithCategoryByCarId(id);

        return ResponseEntity.ok(
                new ResponseUtil(200, "Car with category retrieved successfully", car)
        );
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> updateCar(
            @PathVariable Integer id,
            @ModelAttribute UpdateCarRequestDTO updateCarRequestDTO) {
        updateCarRequestDTO.setCarId(id);

        CarDTO updatedCar = carService.updateCar(updateCarRequestDTO);
        return ResponseEntity.ok(new ResponseUtil(200, "Car updated successfully", updatedCar));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> deleteCar(@PathVariable Integer id) {
        carService.deleteCar(id);
        return ResponseEntity.ok(new ResponseUtil(200, "Car deleted successfully", null));
    }

}
