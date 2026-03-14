package lk.icbt.megacity.service;

import lk.icbt.megacity.dto.DriverDTO;
import lk.icbt.megacity.dto.request.CreateDriverDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DriverService {
    DriverDTO saveDriver(CreateDriverDTO dto, MultipartFile licenseImage);
    List<DriverDTO> getAvailableDrivers();
    void updateDriverStatus(DriverDTO dto);
}
