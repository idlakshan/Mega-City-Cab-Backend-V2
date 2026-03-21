package lk.icbt.megacity.controller;


import lk.icbt.megacity.dto.DriverDTO;
import lk.icbt.megacity.dto.request.CreateDriverDTO;
import lk.icbt.megacity.service.DriverService;
import lk.icbt.megacity.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v2/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> saveDriver(
            @ModelAttribute CreateDriverDTO dto,
            @RequestParam MultipartFile licenseImage
    ) {
        DriverDTO savedDriver = driverService.saveDriver(dto, licenseImage);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseUtil(201, "Driver saved successfully", savedDriver));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> getDriverById(@PathVariable int id) {
        DriverDTO driver = driverService.getDriverById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseUtil(200, "Driver retrieved successfully", driver));
    }
}