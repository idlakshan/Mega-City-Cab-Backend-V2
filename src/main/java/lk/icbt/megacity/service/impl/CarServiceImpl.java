package lk.icbt.megacity.service.impl;

import lk.icbt.megacity.dto.CarDTO;
import lk.icbt.megacity.entity.Car;
import lk.icbt.megacity.entity.Category;
import lk.icbt.megacity.repo.CarRepo;
import lk.icbt.megacity.repo.CategoryRepo;
import lk.icbt.megacity.service.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepo carRepo;
    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    private final String uploadDir = "D:/Projects/ICBT/Mega City Cab/Backend-V2/megacity/uploads/cars/";


    @Override
    public CarDTO saveCar(CarDTO dto, MultipartFile file) {

        if (carRepo.existsByCarNumber(dto.getCarNumber())) {
            throw new RuntimeException("Car number already exists");
        }

        try {

            File folder = new File(uploadDir);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File saveFile = new File(uploadDir + fileName);
            file.transferTo(saveFile);

            Category category = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Car car = Car.builder()
                    .carName(dto.getCarName())
                    .carNumber(dto.getCarNumber())
                    .carImage(fileName)
                    .status("ACTIVE")
                    .category(category)
                    .build();

            carRepo.save(car);

            dto.setCarId(car.getCarId());
            dto.setCarImage(fileName);
            dto.setStatus(car.getStatus());

            return dto;

        } catch (IOException e) {
            throw new RuntimeException("File upload failed");
        }
    }
}
