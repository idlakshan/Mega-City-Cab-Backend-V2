package lk.icbt.megacity.controller;

import jakarta.validation.Valid;
import lk.icbt.megacity.dto.CarDTO;
import lk.icbt.megacity.dto.DriverDTO;
import lk.icbt.megacity.dto.request.BookingRequestDTO;
import lk.icbt.megacity.dto.response.AssignedCarDTO;
import lk.icbt.megacity.dto.response.AssignedDriverDTO;
import lk.icbt.megacity.dto.response.BookingAssignmentResponseDTO;
import lk.icbt.megacity.service.BookingService;
import lk.icbt.megacity.service.CarService;
import lk.icbt.megacity.service.DriverService;
import lk.icbt.megacity.util.ResponseUtil;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/api/v2/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final CarService carService;
    private final DriverService driverService;
    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseUtil> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {

        List<CarDTO> availableCarsByCategory = carService.getAvailableCarsByCategory(bookingRequestDTO.getCategoryId());

        if (availableCarsByCategory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new ResponseUtil(404, "No available cars for the selected category"));
        }

        List<DriverDTO> availableDrivers = driverService.getAvailableDrivers();

        if (availableDrivers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new ResponseUtil(404, "All drivers are booked. Please try again later."));
        }

        Random random = new Random();

        CarDTO assignedCar = availableCarsByCategory.get(random.nextInt(availableCarsByCategory.size()));
        DriverDTO assignedDriver = availableDrivers.get(random.nextInt(availableDrivers.size()));

        AssignedCarDTO assignedCarDTO = modelMapper.map(assignedCar, AssignedCarDTO.class);
        AssignedDriverDTO assignedDriverDTO = modelMapper.map(assignedDriver, AssignedDriverDTO.class);

        BookingAssignmentResponseDTO data =
                new BookingAssignmentResponseDTO(assignedCarDTO, assignedDriverDTO);

        return ResponseEntity.ok(
                new ResponseUtil(200, "Car and driver assigned successfully!", data)
        );

    }


}
