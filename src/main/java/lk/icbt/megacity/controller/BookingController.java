package lk.icbt.megacity.controller;

import jakarta.validation.Valid;
import lk.icbt.megacity.dto.BookingDTO;
import lk.icbt.megacity.dto.CarDTO;
import lk.icbt.megacity.dto.DriverDTO;
import lk.icbt.megacity.dto.request.BookingRequestDTO;
import lk.icbt.megacity.dto.request.BookingStatusUpdateRequestDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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


    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(new ResponseUtil(200, "All bookings retrieved successfully!", bookings));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseUtil> getBookingsByUser(@PathVariable int userId) {
        List<BookingDTO> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(new ResponseUtil(200, "User bookings retrieved successfully!", bookings));
    }

    @GetMapping("/bookings-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> getBookingStats() {
        Map<String, Object> stats = Map.of(
                "totalBookings", bookingService.getTotalBookings(),
                "totalRevenue", bookingService.getTotalRevenue(),
                "activeDrivers", driverService.getActiveDrivers(),        // get active drivers count
                "availableVehicles", carService.getAvailableVehicles()
        );
        return ResponseEntity.ok(new ResponseUtil(200, "Booking statistics retrieved successfully!", stats));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> getBookingsByStatus(@PathVariable String status) {
        List<BookingDTO> bookings = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(new ResponseUtil(200, "Bookings retrieved successfully!", bookings));
    }

    @GetMapping("/last-7-days-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> getLast7DaysData() {
        Map<String, Integer> bookingsLast7Days = bookingService.getBookingCountsLast7Days();
        return ResponseEntity.ok(new ResponseUtil(200, "Last 7 days data retrieved successfully!", bookingsLast7Days));
    }

    @PutMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> updateBookingStatus(
            @Valid @RequestBody BookingStatusUpdateRequestDTO requestDTO
    ) {
        String message = bookingService.updateBookingStatus(requestDTO);

        return ResponseEntity.ok(
                new ResponseUtil(200, message, null)
        );
    }


}
