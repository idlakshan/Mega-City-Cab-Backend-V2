package lk.icbt.megacity.service;

import lk.icbt.megacity.dto.DriverDTO;
import lk.icbt.megacity.dto.request.CreateDriverDTO;
import org.springframework.web.multipart.MultipartFile;

public interface DriverService {
    DriverDTO saveDriver(CreateDriverDTO dto, MultipartFile licenseImage);
}
