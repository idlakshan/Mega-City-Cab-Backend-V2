package lk.icbt.megacity.service.impl;

import lk.icbt.megacity.dto.CarDTO;
import lk.icbt.megacity.dto.UserDTO;
import lk.icbt.megacity.dto.request.CreateCarRequestDTO;
import lk.icbt.megacity.entity.Car;
import lk.icbt.megacity.entity.Category;
import lk.icbt.megacity.repo.CarRepo;
import lk.icbt.megacity.repo.CategoryRepo;
import lk.icbt.megacity.service.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepo carRepo;
    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    private final String uploadDir = "D:/Projects/ICBT/Mega City Cab/Backend-V2/megacity/uploads/cars/";

    @Override
    public CarDTO saveCar(CreateCarRequestDTO carRequestDTO) {

        if (carRepo.existsByCarNumber(carRequestDTO.getCarNumber())) {
            throw new RuntimeException("Car number already exists");
        }

        try {
            File folder = new File(uploadDir);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String fileName = System.currentTimeMillis() + "_" +
                    carRequestDTO.getCarImage().getOriginalFilename();

            File saveFile = new File(uploadDir + fileName);
            carRequestDTO.getCarImage().transferTo(saveFile);

            Category category = categoryRepo.findById(carRequestDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Car car = Car.builder()
                    .carName(carRequestDTO.getCarName())
                    .carNumber(carRequestDTO.getCarNumber())
                    .carImage(fileName)
                    .status("Available")
                    .category(category)
                    .build();

            return modelMapper.map(carRepo.save(car), CarDTO.class);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save car image: " + e.getMessage());
        }
    }

    @Override
    public List<CarDTO> getAvailableCarsByCategory(Integer categoryId) {
        List<Car> availableCarsByCategory = carRepo.getAvailableCarsByCategory(categoryId);
        return modelMapper.map(
                availableCarsByCategory,
                new TypeToken<List<CarDTO>>() {}.getType()
        );

    }

    @Override
    public void updateCarStatus(CarDTO dto) {
        Car car = carRepo.findById(dto.getCarId()).orElseThrow();
        car.setStatus(dto.getStatus());
        carRepo.save(car);
    }
}
