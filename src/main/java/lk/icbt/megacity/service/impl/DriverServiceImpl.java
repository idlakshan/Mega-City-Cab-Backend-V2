package lk.icbt.megacity.service.impl;

import lk.icbt.megacity.dto.DriverDTO;
import lk.icbt.megacity.dto.request.CreateDriverDTO;
import lk.icbt.megacity.entity.Driver;
import lk.icbt.megacity.repo.DriverRepo;
import lk.icbt.megacity.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepo driverRepo;
    private final ModelMapper modelMapper;

    private final String uploadDir = "D:/Projects/ICBT/Mega City Cab/Backend-V2/megacity/uploads/driver/";

    @Override
    public DriverDTO saveDriver(CreateDriverDTO dto, MultipartFile file) {
        if (driverRepo.existsByDriverNic(dto.getDriverNic())) {
            throw new RuntimeException("Driver with this NIC already exists");
        }
        try {
            // Create folder if not exists
            File folder = new File(uploadDir);
            if (!folder.exists()) folder.mkdirs();

            String fileName = null;
            if (file != null && !file.isEmpty()) {
                fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                File saveFile = new File(uploadDir + File.separator + fileName);
                file.transferTo(saveFile);
            }

            Driver driver = modelMapper.map(dto, Driver.class);
            driver.setLicenseImage(fileName);
            driver.setStatus("ACTIVE");

            Driver savedDriver = driverRepo.save(driver);

            return modelMapper.map(savedDriver, DriverDTO.class);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload license image", e);
        }
    }
}
