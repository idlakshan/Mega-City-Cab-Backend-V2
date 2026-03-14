package lk.icbt.megacity.service.impl;
import lk.icbt.megacity.dto.BookingDTO;
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

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final UserRepo userRepo;
    private final CarRepo carRepo;
    private final DriverRepo driverRepo;


    @Override
    public int saveBooking(BookingDTO dto) {

        Booking booking = new Booking();

        booking.setPickupLocation(dto.getPickupLocation());
        booking.setDropLocation(dto.getDropLocation());
        booking.setBookingDateTime(dto.getBookingDateTime().toLocalDateTime());
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
}
