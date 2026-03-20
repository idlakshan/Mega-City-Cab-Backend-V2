package lk.icbt.megacity.service.impl;
import jakarta.persistence.EntityNotFoundException;
import lk.icbt.megacity.dto.BookingDTO;
import lk.icbt.megacity.dto.request.BookingStatusUpdateRequestDTO;
import lk.icbt.megacity.entity.Booking;
import lk.icbt.megacity.entity.Car;
import lk.icbt.megacity.entity.Driver;
import lk.icbt.megacity.entity.User;
import lk.icbt.megacity.repo.BookingRepo;
import lk.icbt.megacity.repo.CarRepo;
import lk.icbt.megacity.repo.DriverRepo;
import lk.icbt.megacity.repo.UserRepo;
import lk.icbt.megacity.service.BookingService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final UserRepo userRepo;
    private final CarRepo carRepo;
    private final DriverRepo driverRepo;
    private final ModelMapper modelMapper;


    @Override
    public int saveBooking(BookingDTO dto) {

        Booking booking = new Booking();

        booking.setPickupLocation(dto.getPickupLocation());
        booking.setDropLocation(dto.getDropLocation());
        booking.setBookingDateTime(dto.getBookingDateTime());
        booking.setCustomerName(dto.getCustomerName());
        booking.setCustomerEmail(dto.getCustomerEmail());
        booking.setCustomerPhone(dto.getCustomerPhone());
        booking.setStatus(dto.getStatus());

        User user = userRepo.findById(dto.getUserId()).orElseThrow();
        Car car = carRepo.findById(dto.getCarId()).orElseThrow();
        Driver driver = driverRepo.findById(dto.getDriverId()).orElseThrow();

        booking.setUser(user);
        booking.setCar(car);
        booking.setDriver(driver);

        Booking saved = bookingRepo.save(booking);
        return saved.getBookingId();
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepo.findAll()
                .stream()
                .map(b -> modelMapper.map(b, BookingDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingsByUserId(int userId) {
        return bookingRepo.findByUserId(userId)
                .stream()
                .map(b -> modelMapper.map(b, BookingDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalBookings() {
        return (int) bookingRepo.count();
    }

    @Override
    public double getTotalRevenue() {
        return bookingRepo.findAll()
                .stream()
                .mapToDouble(b -> b.getPayment().getAmount())
                .sum();
    }

    @Override
    public List<BookingDTO> getBookingsByStatus(String status) {
        return bookingRepo.findByStatus(status)
                .stream()
                .map(b -> modelMapper.map(b, BookingDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Integer> getBookingCountsLast7Days() {
        Map<String, Integer> map = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();

        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            int count = (int) bookingRepo.findAll()
                    .stream()
                    .filter(b -> b.getBookingDateTime().toLocalDate().equals(date))
                    .count();
            map.put(date.toString(), count);
        }

        return map;
    }

    @Override
    public List<BookingDTO> getBookingsDetailsByUserId(int userId) {
        return bookingRepo.findByUserId(userId)
                .stream()
                .map(b -> modelMapper.map(b, BookingDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalBookingsByUserId(int userId) {
        return bookingRepo.findByUserId(userId).size();
    }

    @Override
    public double getTotalSpendingByUserId(int userId) {
        return bookingRepo.findByUserId(userId)
                .stream()
                .mapToDouble(b -> b.getPayment().getAmount())
                .sum();
    }

    @Override
    public String getActiveSinceByUserId(int userId) {
        Optional<Booking> firstBooking = bookingRepo.findByUserId(userId)
                .stream()
                .min(Comparator.comparing(Booking::getBookingDateTime));
        return firstBooking.map(b -> String.valueOf(b.getBookingDateTime().getYear())).orElse(null);
    }

    @Override
    public String getFavoriteLocationByUserId(int userId) {
        Map<String, Long> locationCounts = bookingRepo.findByUserId(userId)
                .stream()
                .collect(Collectors.groupingBy(Booking::getPickupLocation, Collectors.counting()));

        return locationCounts.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    @Override
    public String updateBookingStatus(BookingStatusUpdateRequestDTO requestDTO) {
        Booking booking = bookingRepo.findById(requestDTO.getBookingId())
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        String status = requestDTO.getStatus();

        if (!"Completed".equals(status) && !"Canceled".equals(status)) {
            throw new IllegalArgumentException("Invalid status update.");
        }

        booking.setStatus(status);
        bookingRepo.save(booking);

        Car car = booking.getCar();
        if (car != null) {
            car.setStatus("Available");
            carRepo.save(car);
        }

        Driver driver = booking.getDriver();
        if (driver != null) {
            driver.setStatus("Available");
            driverRepo.save(driver);
        }

        return "Completed".equals(status)
                ? "Booking status updated to Completed and car/driver status updated to Available."
                : "Booking status updated to Canceled and car/driver status updated to Available.";
    }

    @Override
    public BookingDTO getBookingById(int i) {
        Booking bookingByBookingId = bookingRepo.getBookingByBookingId(i);
        return modelMapper.map(bookingByBookingId,BookingDTO.class);
    }

}
