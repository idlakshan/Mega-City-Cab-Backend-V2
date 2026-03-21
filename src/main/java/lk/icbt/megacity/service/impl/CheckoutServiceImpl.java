package lk.icbt.megacity.service.impl;

import jakarta.transaction.Transactional;
import lk.icbt.megacity.dto.BookingDTO;
import lk.icbt.megacity.dto.CarDTO;
import lk.icbt.megacity.dto.DriverDTO;
import lk.icbt.megacity.dto.PaymentDTO;
import lk.icbt.megacity.dto.request.StripeCheckoutRequestDTO;
import lk.icbt.megacity.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final BookingService bookingService;
    private final DriverService driverService;
    private final CarService carService;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public int processCheckout(StripeCheckoutRequestDTO request) {

        BookingDTO bookingDTO = modelMapper.map(request, BookingDTO.class);
        bookingDTO.setStatus("InProgress");
        bookingDTO.setBookingDateTime(request.getBookingDateTime());

        // Save booking
        int bookingId = bookingService.saveBooking(bookingDTO);

        // Update driver
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setDriverId(request.getDriverId());
        driverDTO.setStatus("Assigned");
        driverService.updateDriverStatus(driverDTO);

        // Update car
        CarDTO carDTO = new CarDTO();
        carDTO.setCarId(request.getCarId());
        carDTO.setStatus("Booked");
        carService.updateCarStatus(carDTO);

        // Save payment
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setBookingId(bookingId);
        paymentDTO.setAmount(request.getAmount());
        paymentDTO.setPaymentMethod("Stripe");
        paymentDTO.setPaymentStatus("Success");
        paymentDTO.setPaymentDate(LocalDateTime.now());
        paymentService.savePayment(paymentDTO);

        return bookingId;
    }
}
