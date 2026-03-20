package lk.icbt.megacity.service.impl;

import jakarta.transaction.Transactional;
import lk.icbt.megacity.dto.CarDTO;
import lk.icbt.megacity.dto.CategoryDTO;
import lk.icbt.megacity.dto.UserDTO;
import lk.icbt.megacity.dto.projection.CarWithCategoryProjection;
import lk.icbt.megacity.dto.request.CreateCarRequestDTO;
import lk.icbt.megacity.dto.request.UpdateCarRequestDTO;
import lk.icbt.megacity.dto.response.CarWithCategoryDTO;
import lk.icbt.megacity.entity.Car;
import lk.icbt.megacity.entity.Category;
import lk.icbt.megacity.repo.CarRepo;
import lk.icbt.megacity.repo.CategoryRepo;
import lk.icbt.megacity.service.CarService;
import lk.icbt.megacity.service.CloudinaryService;
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
    private final CloudinaryService cloudinaryService;


    @Override
    public CarDTO saveCar(CreateCarRequestDTO carRequestDTO) {

        if (carRepo.existsByCarNumber(carRequestDTO.getCarNumber())) {
            throw new RuntimeException("Car number already exists");
        }

        String imageUrl = cloudinaryService.uploadFile(
                carRequestDTO.getCarImage(),
                "cars"
        );

        Category category = categoryRepo.findById(carRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Car car = Car.builder()
                .carName(carRequestDTO.getCarName())
                .carNumber(carRequestDTO.getCarNumber())
                .carImage(imageUrl)
                .status("Available")
                .category(category)
                .build();

        return modelMapper.map(carRepo.save(car), CarDTO.class);

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

    @Override
    public int getAvailableVehicles() {
        return carRepo.countByStatus("Available");
    }

    @Override
    public List<CarDTO> getAllCars() {
        List<Car> all = carRepo.findAll();

        return modelMapper.map(all,new TypeToken<List<CarDTO>>() {}.getType());
    }

    @Override
    public CarDTO getCarById(int id) {
        Car car = carRepo.getCarByCarId(id);
        if (car == null) {
            throw new RuntimeException("Car not found with id: " + id);
        }
        return modelMapper.map(car,CarDTO.class);
    }

    @Override
    public CarWithCategoryDTO getCarWithCategoryByCarId(Integer id) {
        CarWithCategoryProjection projection = carRepo.getCarWithCategoryByCarId(id);
        if (projection == null) {
            throw new RuntimeException("Car not found with id: " + id);
        }

        CarWithCategoryDTO dto = new CarWithCategoryDTO();
        dto.setCarId(projection.getCarId());
        dto.setCarName(projection.getCarName());
        dto.setCarNumber(projection.getCarNumber());
        dto.setCarImage(projection.getCarImage());
        dto.setStatus(projection.getStatus());
        dto.setCategoryId(projection.getCategoryId());
        dto.setCategoryName(projection.getCategoryName());

        return dto;
    }

    @Override
    @Transactional
    public CarDTO updateCar(UpdateCarRequestDTO updateCarRequestDTO) {
        Car existingCar = carRepo.findById(updateCarRequestDTO.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + updateCarRequestDTO.getCarId()));

        if (!existingCar.getCarNumber().equals(updateCarRequestDTO.getCarNumber()) &&
                carRepo.existsByCarNumber(updateCarRequestDTO.getCarNumber())) {
            throw new RuntimeException("Car number already exists");
        }

        if (updateCarRequestDTO.getCategoryId() != null &&
                !existingCar.getCategory().getId().equals(updateCarRequestDTO.getCategoryId())) {
            Category category = categoryRepo.findById(updateCarRequestDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existingCar.setCategory(category);
        }

        if (updateCarRequestDTO.getCarName() != null) {
            existingCar.setCarName(updateCarRequestDTO.getCarName());
        }

        if (updateCarRequestDTO.getCarNumber() != null) {
            existingCar.setCarNumber(updateCarRequestDTO.getCarNumber());
        }

        if (updateCarRequestDTO.getStatus() != null) {
            existingCar.setStatus(updateCarRequestDTO.getStatus());
        }

        if (updateCarRequestDTO.getCarImage() != null && !updateCarRequestDTO.getCarImage().isEmpty()) {
            String newImageUrl = cloudinaryService.uploadFile(
                    updateCarRequestDTO.getCarImage(),
                    "cars"
            );
            existingCar.setCarImage(newImageUrl);
        }

        Car updatedCar = carRepo.save(existingCar);

        return modelMapper.map(updatedCar, CarDTO.class);
    }
}
